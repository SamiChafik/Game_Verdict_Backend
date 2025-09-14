package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findTop3ByOrderByReleaseDateDesc();
}