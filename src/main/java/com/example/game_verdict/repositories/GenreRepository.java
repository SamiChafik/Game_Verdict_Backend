package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
