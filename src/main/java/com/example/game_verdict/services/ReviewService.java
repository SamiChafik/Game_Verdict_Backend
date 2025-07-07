package com.example.game_verdict.services;

import com.example.game_verdict.dtos.ReviewDTO;
import com.example.game_verdict.entities.*;
import com.example.game_verdict.exceptions.ResourceNotFoundException;
import com.example.game_verdict.mappers.ReviewMapper;
import com.example.game_verdict.repositories.GameRepository;
import com.example.game_verdict.repositories.ReviewRepository;
import com.example.game_verdict.repositories.ReviewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;
    private final ReviewerRepository reviewerRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         GameRepository gameRepository,
                         ReviewerRepository reviewerRepository,
                         ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
        this.reviewerRepository = reviewerRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviewMapper.toDTOs(reviews);
    }

    @Transactional(readOnly = true)
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return reviewMapper.toDTO(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getReviewsByGameId(Long gameId) {
        List<Review> reviews = reviewRepository.findByGameId(gameId);
        return reviewMapper.toDTOs(reviews);
    }

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Game game = gameRepository.findById(reviewDTO.getGameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + reviewDTO.getGameId()));

        Reviewer reviewer = reviewerRepository.findById(reviewDTO.getReviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found with id: " + reviewDTO.getReviewerId()));

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setGame(game);
        review.setReviewer(reviewer);
        review.setCreatedAt(LocalDate.now());

        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        existingReview.setContent(reviewDTO.getContent());
        existingReview.setRating(reviewDTO.getRating());

        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.toDTO(updatedReview);
    }

    @Transactional
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public int getCommentCount(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> review.getComments().size())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
    }
}