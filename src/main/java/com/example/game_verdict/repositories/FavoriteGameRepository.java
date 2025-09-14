package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.FavoriteGame;
import com.example.game_verdict.entities.Game;
import com.example.game_verdict.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteGameRepository extends JpaRepository<FavoriteGame, Long> {
    List<FavoriteGame> findByUser(User user);
    Optional<FavoriteGame> findByUserAndGame(User user, Game game);
}
