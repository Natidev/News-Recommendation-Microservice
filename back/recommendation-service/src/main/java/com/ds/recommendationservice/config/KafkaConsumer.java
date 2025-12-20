package com.ds.recommendationservice.config;

import com.ds.recommendationservice.services.EnrichedNewsConsumerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final EnrichedNewsConsumerService enrichedNewsConsumerService;

    @PostConstruct
    public void start() {
        log.info("Starting Kafka Consumer");
        enrichedNewsConsumerService.consume();
    }

}
