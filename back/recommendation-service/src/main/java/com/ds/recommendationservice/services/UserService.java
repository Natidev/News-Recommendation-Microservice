package com.ds.recommendationservice.services;

import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.UserRecommendationProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public Flux<UserRecommendationProfile> findInterestedUsers(EnrichedNewsPayload news) {
        return getMockUserRecommendationProfiles();
    }

    private Flux<UserRecommendationProfile> getMockUserRecommendationProfiles() {
        List<UserRecommendationProfile> mockUsers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            mockUsers.add(UserRecommendationProfile.builder()
                    .userId("user-12" + i)
                    .username("johndoe" + i)
                    .preferences(UserRecommendationProfile.Preferences.builder()
                            .preferredCategories(List.of("Technology", "Finance"))
                            .blockedSources(List.of("spam-news.com", "fake-news.org"))
                            .build())
                    .demographics(UserRecommendationProfile.Demographics.builder()
                            .ageRange("25-34")
                            .location("US")
                            .build())
                    .engagementMetrics(UserRecommendationProfile.EngagementMetrics.builder()
                            .lastActive(Instant.now())
                            .averageReadTimeSeconds(120.5)
                            .totalArticlesRead(350)
                            .build())
                    .build());
        }
        return Flux.fromIterable(mockUsers);
    }
}
