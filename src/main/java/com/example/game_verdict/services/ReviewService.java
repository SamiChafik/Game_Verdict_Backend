package com.example.game_verdict.services;

import com.example.game_verdict.dtos.ReviewDTO;
import com.example.game_verdict.entities.*;
import com.example.game_verdict.exceptions.ResourceNotFoundException;
import com.example.game_verdict.mappers.ReviewMapper;
import com.example.game_verdict.repositories.GameRepository;
import com.example.game_verdict.repositories.ReviewRepository;
import com.example.game_verdict.repositories.ReviewerRepository;
import com.example.game_verdict.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;
    private final ReviewerRepository reviewerRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         GameRepository gameRepository,
                         ReviewerRepository reviewerRepository,
                         UserRepository userRepository,
                         ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
        this.reviewerRepository = reviewerRepository;
        this.userRepository = userRepository;
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
        updateAverageRating(game.getId());
        return reviewMapper.toDTO(savedReview);
    }

    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        existingReview.setContent(reviewDTO.getContent());
        existingReview.setRating(reviewDTO.getRating());

        Review updatedReview = reviewRepository.save(existingReview);
        updateAverageRating(existingReview.getGame().getId());
        return reviewMapper.toDTO(updatedReview);
    }

    @Transactional
    public void deleteReview(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();
        User currentUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));

        boolean isOwner = review.getReviewer().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isModerator = currentUser.getRole() == Role.MODERATOR;

        if (isOwner || isAdmin || isModerator) {
            reviewRepository.deleteById(id);
            updateAverageRating(review.getGame().getId());
        } else {
            throw new RuntimeException("You are not authorized to delete this review.");
        }
    }

    public void updateAverageRating(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameId));

        List<Review> reviews = reviewRepository.findByGameId(gameId);

        if (reviews.isEmpty()) {
            game.setAverageRating(0.0f);
        } else {
            double sum = 0;
            for (Review review : reviews) {
                sum += review.getRating().getValue();
            }
            game.setAverageRating((float) (sum / reviews.size()));
        }

        gameRepository.save(game);
    }

    @Transactional(readOnly = true)
    public int getCommentCount(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(review -> review.getComments().size())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
    }
}