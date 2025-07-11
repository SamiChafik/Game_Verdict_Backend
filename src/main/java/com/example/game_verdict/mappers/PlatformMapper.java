package com.example.game_verdict.mappers;

import com.example.game_verdict.dtos.PlatformDTO;
import com.example.game_verdict.entities.Platform;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlatformMapper {
    Platform toEntity(PlatformDTO dto);
    PlatformDTO toDTO(Platform platform);
    List<PlatformDTO> toDTOs(List<Platform> Platforms);
}
