package com.marketguild.player_service.consumer;

import com.marketguild.player_service.event.ItemBoughtEvent;
import com.marketguild.player_service.model.Player;
import com.marketguild.player_service.service.PlayerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ItemBoughtConsumer {
    private final PlayerService playerService;

    public ItemBoughtConsumer(PlayerService playerService) {
        this.playerService = playerService;
    }

    @KafkaListener(topics = "item-bought", groupId = "player-service-group")
    public void consume(ItemBoughtEvent event){
        Player targetedPlayer = playerService.findPlayerById(event.playerId());
        playerService.updateBalance(targetedPlayer.getId(), event.price());
    }
}
