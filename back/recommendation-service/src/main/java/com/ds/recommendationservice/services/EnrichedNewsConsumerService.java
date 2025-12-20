package com.ds.recommendationservice.services;

import com.ds.recommendationservice.models.EnrichedNewsPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrichedNewsConsumerService {

    private final KafkaReceiver<String, EnrichedNewsPayload> kafkaReceiver;
    private final RecommendationService recommendationService;

    public void consume(){
        kafkaReceiver.receive()
                .map(ConsumerRecord::value)
                .flatMap(recommendationService::process)
                .subscribe();
    }

}
