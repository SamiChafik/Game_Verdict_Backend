package com.example.game_verdict.mappers;

import com.example.game_verdict.dtos.FavoriteGameDTO;
import com.example.game_verdict.entities.FavoriteGame;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteGameMapper {
    FavoriteGame toEntity(FavoriteGameDTO dto);
    FavoriteGameDTO toDTO(FavoriteGame favoriteGame);
    List<FavoriteGameDTO> toDTOs(List<FavoriteGame> favoriteGames);
}
