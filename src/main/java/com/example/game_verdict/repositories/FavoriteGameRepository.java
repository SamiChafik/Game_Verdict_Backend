package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.FavoriteGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteGameRepository extends JpaRepository<FavoriteGame, Long> {
    List<FavoriteGame> findByUserId(Long userId);
    boolean existsByUserIdAndGameId(Long userId, Long gameId);
    Optional<FavoriteGame> findByUserIdAndGameId(Long userId, Long gameId);
}