package com.ds.recommendationservice.config;

import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.RecommendationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;

    @Value("${news.enriched_news_topic}")
    private String enriched_news_topic;

    @Bean
    public SenderOptions<String, RecommendationCreatedEvent> senderOptions(ProducerFactory<String, RecommendationCreatedEvent> producerFactory){
        Map<String, Object> config = new HashMap<>(producerFactory.getConfigurationProperties());
        return SenderOptions.create(config);
    }

    @Bean
    public KafkaSender<String, RecommendationCreatedEvent> kafkaSender(SenderOptions<String, RecommendationCreatedEvent> senderOptions){
        return KafkaSender.create(senderOptions);
    }

    @Bean
    public ReceiverOptions<String, EnrichedNewsPayload> receiverOptions(){
        Map<String, Object> config = new HashMap<>(kafkaProperties.buildConsumerProperties());
        return ReceiverOptions.<String, EnrichedNewsPayload>create(config)
                .subscription(Collections.singleton(enriched_news_topic));
    }

    @Bean
    public KafkaReceiver<String, EnrichedNewsPayload> kafkaReceiver(ReceiverOptions<String, EnrichedNewsPayload> receiverOptions){
        return KafkaReceiver.create(receiverOptions);
    }
}
