package com.marketguild.player_service.controller;

import com.marketguild.player_service.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/players")
public class PlayerInternalController {
    private final PlayerService playerService;

    public PlayerInternalController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PatchMapping("/{id}/balance")
    public void updateBalance(@PathVariable Long id, @RequestBody Double price){
        playerService.updateBalance(id, price);
    }
}
