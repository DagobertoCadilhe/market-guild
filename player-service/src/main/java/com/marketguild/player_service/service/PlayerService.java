package com.marketguild.player_service.service;

import com.marketguild.player_service.model.Player;
import com.marketguild.player_service.repository.PlayerRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(String name, Double balance){
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer.setBalance(balance);

        playerRepository.save(newPlayer);

        return newPlayer;
    }

    public Player findPlayerById(Long playerId){
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: "+ playerId));
        return player;
    }

    public void updateBalance(Long playerId, double price){
        Player player = findPlayerById(playerId);

        player.setBalance((player.getBalance()) - price);
        playerRepository.save(player);
    }
}
