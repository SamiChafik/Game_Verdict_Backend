package com.example.game_verdict.controllers;

import com.example.game_verdict.dtos.ReviewDTO;
import com.example.game_verdict.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin("*")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MEMBER', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MEMBER', 'MODERATOR')")
    @GetMapping("/game/{gameId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByGame(@PathVariable Long gameId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByGameId(gameId);
        return ResponseEntity.ok(reviews);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MEMBER', 'MODERATOR')")
    @GetMapping("/{id}/comment-count")
    public ResponseEntity<Integer> getCommentCount(@PathVariable Long id) {
        int count = reviewService.getCommentCount(id);
        return ResponseEntity.ok(count);
    }

    @PreAuthorize("hasRole('REVIEWER')")
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER')")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id,
                                                  @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}