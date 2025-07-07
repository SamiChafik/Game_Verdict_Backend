package com.example.game_verdict.mappers;

import com.example.game_verdict.dtos.GameDTO;
import com.example.game_verdict.entities.Game;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    Game toEntity(GameDTO dto);
    GameDTO toDTO(Game game);
    List<GameDTO> toDTOs(List<Game> games);
}
