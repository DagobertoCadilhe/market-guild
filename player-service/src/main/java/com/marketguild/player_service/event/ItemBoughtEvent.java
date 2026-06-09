package com.marketguild.player_service.event;

public record ItemBoughtEvent(Long playerId, String itemId, Double price) {
}