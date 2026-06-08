package com.marketguild.player_service.repository;

import com.marketguild.player_service.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}