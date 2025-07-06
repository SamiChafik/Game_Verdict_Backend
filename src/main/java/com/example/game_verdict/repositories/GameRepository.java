package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
