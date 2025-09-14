package com.example.game_verdict.repositories;

import com.example.game_verdict.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId(Long reviewId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.review.id = :reviewId")
    List<Comment> findByReviewIdWithUser(Long reviewId);
}
