package com.marketguild.player_service.consumer;

import com.marketguild.player_service.event.ItemBoughtEvent;
import com.marketguild.player_service.model.Player;
import com.marketguild.player_service.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ItemBoughtConsumer {
    private final PlayerService playerService;
    private static final Logger log = LoggerFactory.getLogger(ItemBoughtConsumer.class);

    public ItemBoughtConsumer(PlayerService playerService) {
        this.playerService = playerService;
    }

    @KafkaListener(topics = "item-bought", groupId = "player-service-group")
    public void consume(ItemBoughtEvent event){
        log.info("[{}] Consuming item-bought event | player: {} | item: {} | price: {}",
                event.correlationId(), event.playerId(), event.itemId(), event.price());

        Player targetedPlayer = playerService.findPlayerById(event.playerId());
        playerService.updateBalance(targetedPlayer.getId(), event.price());
        playerService.addItemToBag(targetedPlayer.getId(), event.itemId());

        log.info("[{}] Balance debited and item {} added to the player {} bag",
                event.correlationId(), event.itemId(), event.playerId());
    }
}
