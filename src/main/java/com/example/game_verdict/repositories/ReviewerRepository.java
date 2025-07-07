package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
}
