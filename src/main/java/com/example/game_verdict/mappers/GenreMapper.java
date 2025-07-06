package com.example.game_verdict.mappers;

import com.example.game_verdict.dtos.GenreDTO;
import com.example.game_verdict.entities.Genre;

import java.util.List;

public interface GenreMapper {
    Genre toEntity(GenreDTO dto);
    GenreDTO toDTO(Genre genre);
    List<GenreDTO> toDTOs(List<Genre> Genres);
}
