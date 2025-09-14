package com.example.game_verdict.services;

import com.example.game_verdict.dtos.UserDTO;
import com.example.game_verdict.entities.User;
import com.example.game_verdict.mappers.UserMapper;
import com.example.game_verdict.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper mapper, UserRepository repository, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setBio(dto.getBio());
        user.setAvatar(dto.getAvatar());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        User savedUser = repository.save(user);
        return mapper.toDTO(savedUser);
    }

    public UserDTO updateUserRole(Long id, UserDTO dto){
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(dto.getRole());
        User savedUser = repository.save(user);
        return mapper.toDTO(savedUser);
    }

    public UserDTO updateUserStatus(Long id, Boolean isBanned){
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBanned(isBanned);
        User savedUser = repository.save(user);
        return mapper.toDTO(savedUser);
    }

    public List<UserDTO> getUsersList(){
        List<User> users = repository.findAll();
        return mapper.toDTOs(users);
    }

    public void deleteUser(Long id){
        repository.deleteById(id);
    }

    public void deleteCurrentUser(Principal principal) {
        User user = repository.findByEmail(principal.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        repository.delete(user);
    }
}