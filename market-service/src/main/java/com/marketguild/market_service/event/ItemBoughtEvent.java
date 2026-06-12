package com.marketguild.market_service.event;

public record ItemBoughtEvent(Long playerId, String itemId, Double price, String correlationId) {
}
