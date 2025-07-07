package com.example.game_verdict.mappers;


import com.example.game_verdict.dtos.CommentDTO;
import com.example.game_verdict.entities.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentDTO dto);
    CommentDTO toDTO(Comment comment);
    List<CommentDTO> toDTOs(List<Comment> comments);
}
