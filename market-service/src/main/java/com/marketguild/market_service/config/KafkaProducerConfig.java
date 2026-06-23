package com.marketguild.market_service.config;

import com.marketguild.market_service.event.ItemBoughtEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, ItemBoughtEvent> kafkaTemplate(
            ProducerFactory<String, ItemBoughtEvent> producerFactory) {
        KafkaTemplate<String, ItemBoughtEvent> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setObservationEnabled(true);
        return kafkaTemplate;
    }
}