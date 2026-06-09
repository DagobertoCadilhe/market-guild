package com.marketguild.market_service.producer;

import com.marketguild.market_service.event.ItemBoughtEvent;
import com.marketguild.market_service.model.Item;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ItemBoughtProducer {
    private final KafkaTemplate<String, ItemBoughtEvent> kafkaTemplate;

    public ItemBoughtProducer(KafkaTemplate<String, ItemBoughtEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishOrderEvent(ItemBoughtEvent event){
        kafkaTemplate.send("item-bought", event);
    }
}
