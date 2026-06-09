package com.marketguild.market_service.client;

import com.marketguild.market_service.dto.PlayerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PlayerClientFallback implements PlayerClient {
    @Override
    public PlayerDTO findById(Long playerId){
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Player service unavailable");
    }
}
