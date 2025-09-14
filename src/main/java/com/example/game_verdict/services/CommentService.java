package com.example.game_verdict.services;

import com.example.game_verdict.dtos.CommentDTO;
import com.example.game_verdict.entities.Comment;
import com.example.game_verdict.entities.Review;
import com.example.game_verdict.entities.Role;
import com.example.game_verdict.entities.User;
import com.example.game_verdict.exceptions.ResourceNotFoundException;
import com.example.game_verdict.mappers.CommentMapper;
import com.example.game_verdict.repositories.CommentRepository;
import com.example.game_verdict.repositories.ReviewRepository;
import com.example.game_verdict.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          ReviewRepository reviewRepository,
                          UserRepository userRepository,
                          CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return commentMapper.toDTOs(comments);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByReviewId(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewIdWithUser(reviewId);
        return commentMapper.toDTOs(comments);
    }

    @Transactional(readOnly = true)
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        return commentMapper.toDTO(comment);
    }

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        Review review = reviewRepository.findById(commentDTO.getReviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + commentDTO.getReviewId()));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + commentDTO.getUserId()));

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setReview(review);
        comment.setUser(user);
        comment.setCreatedAt(LocalDate.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment);
    }

    @Transactional
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        existingComment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(existingComment);
        return commentMapper.toDTO(updatedComment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isOwner = comment.getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);
        boolean isModerator = currentUser.getRole().equals(Role.MODERATOR);

        if (!isOwner && !isAdmin && !isModerator) {
            throw new RuntimeException("You are not authorized to delete this comment");
        }

        commentRepository.deleteById(id);
    }
}