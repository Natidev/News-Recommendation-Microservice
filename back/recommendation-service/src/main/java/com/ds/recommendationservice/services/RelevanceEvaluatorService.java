package com.ds.recommendationservice.services;

import com.ds.recommendationservice.models.Decision;
import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.UserRecommendationProfile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class RelevanceEvaluatorService {
    public Mono<Decision> evaluate(EnrichedNewsPayload news, UserRecommendationProfile user) {
        // Check blocked sources
        if (user.getPreferences().getBlockedSources().contains(news.getProvider())) {
            return Mono.just(Decision.notRelevant(news, user));
        }

        // Check preferred categories


        // Optional: consider engagement metrics (e.g., last active within X days)
        Instant lastActive = user.getEngagementMetrics().getLastActive();
        boolean activeRecently = lastActive != null && lastActive.isAfter(Instant.now().minus(30, ChronoUnit.DAYS));

        if (!activeRecently) {
            return Mono.just(Decision.notRelevant(news, user));
        }

        // If all checks passed, relevant
        return Mono.just(Decision.relevant(news, user, "category match"));
    }
}
