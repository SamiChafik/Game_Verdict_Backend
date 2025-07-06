package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
