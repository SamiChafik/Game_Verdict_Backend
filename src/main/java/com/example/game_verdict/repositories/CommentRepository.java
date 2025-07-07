package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId(Long reviewId);
}
