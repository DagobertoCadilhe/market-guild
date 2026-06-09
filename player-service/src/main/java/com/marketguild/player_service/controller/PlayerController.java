package com.marketguild.player_service.controller;

import com.marketguild.player_service.dto.PlayerRequest;
import com.marketguild.player_service.model.Player;
import com.marketguild.player_service.repository.PlayerRepository;
import com.marketguild.player_service.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")/
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }



    @PostMapping()
    public Player createPlayer(@RequestBody PlayerRequest playerDTO){

        Player newPlayer = playerService.createPlayer(playerDTO.getName(), playerDTO.getBalance());

        return newPlayer;
    }

    @GetMapping("/{id}")
    public Player findPlayerById(@PathVariable Long id){
        return playerService.findPlayerById(id);
    }

    @PatchMapping("/{id}/balance")
    public void updateBalance(@PathVariable Long id, @RequestBody Double price){
        playerService.updateBalance(id, price);
    }
}
