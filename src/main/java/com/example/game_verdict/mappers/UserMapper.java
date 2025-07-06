package com.example.game_verdict.mappers;

import com.example.game_verdict.dtos.UserDTO;
import com.example.game_verdict.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO dto);
    UserDTO toDTO(User user);
    List<UserDTO> toDTOs(List<User> users);
}
