package com.example.game_verdict.mappers;


import com.example.game_verdict.dtos.CommentDTO;
import com.example.game_verdict.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "review", ignore = true)
    @Mapping(target = "member", ignore = true)
    Comment toEntity(CommentDTO dto);

    @Mapping(target = "reviewId", source = "review.id")
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberUsername", source = "member.username")
    CommentDTO toDTO(Comment comment);

    List<CommentDTO> toDTOs(List<Comment> comments);
}
