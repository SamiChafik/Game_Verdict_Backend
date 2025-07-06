package com.example.game_verdict.mappers;

import com.example.game_verdict.dtos.ReviewDTO;
import com.example.game_verdict.entities.Review;

import java.util.List;

public interface ReviewMapper {
    Review toEntity(ReviewDTO dto);
    ReviewDTO toDTO(Review review);
    List<ReviewDTO> toDTOs(List<Review> reviews);
}
