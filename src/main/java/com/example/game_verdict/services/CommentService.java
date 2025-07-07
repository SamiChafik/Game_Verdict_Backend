package com.example.game_verdict.services;

import com.example.game_verdict.dtos.CommentDTO;
import com.example.game_verdict.entities.*;
import com.example.game_verdict.exceptions.ResourceNotFoundException;
import com.example.game_verdict.mappers.CommentMapper;
import com.example.game_verdict.repositories.CommentRepository;
import com.example.game_verdict.repositories.MemberRepository;
import com.example.game_verdict.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          ReviewRepository reviewRepository,
                          MemberRepository memberRepository,
                          CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return commentMapper.toDTOs(comments);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO> getCommentsByReviewId(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewId(reviewId);
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

        Member member = memberRepository.findById(commentDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + commentDTO.getMemberId()));

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setReview(review);
        comment.setMember(member);
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
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }
}