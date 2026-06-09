package com.marketguild.market_service.client;

import com.marketguild.market_service.dto.PlayerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "player-service", fallback = PlayerClientFallback.class)
public interface PlayerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/players/{playerId}")
    PlayerDTO findById(@PathVariable Long playerId);

}
