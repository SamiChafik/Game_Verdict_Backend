package com.example.game_verdict.mappers;


import com.example.game_verdict.dtos.CommentDTO;
import com.example.game_verdict.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "review", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment toEntity(CommentDTO dto);

    @Mapping(target = "reviewId", source = "review.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.name")
    CommentDTO toDTO(Comment comment);

    List<CommentDTO> toDTOs(List<Comment> comments);
}
