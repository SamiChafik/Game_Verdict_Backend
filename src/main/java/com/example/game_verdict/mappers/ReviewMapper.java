package com.example.game_verdict.mappers;

import com.example.game_verdict.dtos.ReviewDTO;
import com.example.game_verdict.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "game", ignore = true)
    @Mapping(target = "reviewer", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Review toEntity(ReviewDTO dto);

    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "gameTitle", source = "game.title")
    @Mapping(target = "reviewerId", source = "reviewer.id")
    @Mapping(target = "reviewerUsername", source = "reviewer.name")
    @Mapping(target = "commentCount", expression = "java(review.getComments().size())")
    ReviewDTO toDTO(Review review);
    List<ReviewDTO> toDTOs(List<Review> reviews);
}
