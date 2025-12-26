package com.ds.recommendationservice.services;

import com.ds.recommendationservice.models.Decision;
import com.ds.recommendationservice.models.RecommendationCreatedEvent;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationStore {
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;
    private final ObjectMapper objectMapper;

    public Mono<Void> saveToRedis(Decision decision) {
        RecommendationCreatedEvent event = RecommendationCreatedEvent.builder()
                .userId(decision.user().getUserId())
                .newsId(decision.news().getId())
                .score(decision.score())
                .reason(decision.reason())
                .timestamp(Instant.now())
                .build();
        String key = "recommendation:" + decision.user().getUserId();

        return reactiveRedisTemplate.opsForList()
                .rightPush(key, event)
                .flatMap(count -> reactiveRedisTemplate.expire(key, Duration.ofHours(24)))
                .doOnSuccess(v -> log.info("Saved recommendation to Redis: {}", event))
                .doOnError(err -> log.error("Failed to save recommendation", err))
                .then();
    }

    public Flux<RecommendationCreatedEvent> getRecommendations(String userId) {
        String key = "recommendation:" + userId;
        return reactiveRedisTemplate.opsForList()
                .range(key, 0, -1)
                .map(obj -> objectMapper.convertValue(obj, RecommendationCreatedEvent.class));
    }
}
