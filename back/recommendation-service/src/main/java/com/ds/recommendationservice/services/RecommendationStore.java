package com.ds.recommendationservice.services;

import com.ds.recommendationservice.models.Decision;
import com.ds.recommendationservice.models.RecommendationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationStore {
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public Mono<Void> saveToRedis(Decision decision) {
        RecommendationCreatedEvent event = RecommendationCreatedEvent.builder()
                .userId(decision.user().getUserId())
                .newsId(decision.news().getId())
                .score(null)
                .reason(decision.reason())
                .timestamp(Instant.now())
                .build();
    String key = "recommendation:"+decision.user().getUserId();

    return reactiveRedisTemplate.opsForList()
            .rightPush(key, event)
            .flatMap(count -> reactiveRedisTemplate.expire(key, Duration.ofHours(24)))
            .doOnSuccess(v -> log.info("Saved recommendation to Redis: {}", event))
            .doOnError(err -> log.error("Failed to save recommendation", err))
            .then();

    }
}
