package com.marketguild.market_service.client;

import com.marketguild.market_service.dto.PlayerDTO;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerClientService {

    private final PlayerClient playerClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public PlayerClientService(PlayerClient playerClient, CircuitBreakerFactory circuitBreakerFactory) {
        this.playerClient = playerClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public PlayerDTO findById(Long playerId) {
        return circuitBreakerFactory.create("player-service")
                .run(
                        () -> playerClient.findById(playerId),
                        throwable -> {
                            throw new ResponseStatusException(
                                    HttpStatus.SERVICE_UNAVAILABLE,
                                    "player-service indisponível"
                            );
                        }
                );
    }
}