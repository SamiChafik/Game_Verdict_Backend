package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.FavoriteGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteGame, Long> {
}
