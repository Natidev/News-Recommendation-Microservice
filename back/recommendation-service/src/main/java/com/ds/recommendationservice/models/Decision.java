package com.ds.recommendationservice.models;

public record Decision(EnrichedNewsPayload news, UserRecommendationProfile user, boolean relevant, String reason) {
    public static Decision relevant(EnrichedNewsPayload news, UserRecommendationProfile user, String reason) {
        return new Decision(news, user, true, reason);
    }

    public static Decision notRelevant(EnrichedNewsPayload news, UserRecommendationProfile user) {
        return new Decision(news, user, false, "not relevant");
    }

    public boolean isRelevant() {
        return relevant;
    }
}
