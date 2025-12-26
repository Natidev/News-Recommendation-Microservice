package com.ds.recommendationservice.controllers;


import com.ds.recommendationservice.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{id}")
    public Flux<RecommendationService.EnrichedRecommendation> getRecommendations(@PathVariable String id) {
        return recommendationService.getRecommendationForUser(id);
    }
}
