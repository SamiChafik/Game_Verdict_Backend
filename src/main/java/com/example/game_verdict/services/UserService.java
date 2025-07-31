package com.example.game_verdict.services;

import com.example.game_verdict.dtos.UserDTO;
import com.example.game_verdict.entities.Role;
import com.example.game_verdict.entities.User;
import com.example.game_verdict.mappers.UserMapper;
import com.example.game_verdict.repositories.UserRepository;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

//    public UserDTO createUser(UserDTO userDTO) {
//        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
//            throw new IllegalArgumentException("Email is required");
//        }
//        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
//            throw new IllegalArgumentException("Password is required");
//        }
//
//        User existingUser = repository.findByEmail(userDTO.getEmail());
//        if (existingUser != null) {
//            throw new RuntimeException("Email already in use");
//        }
//
//        User user = new User();
//        user.setName(userDTO.getName());
//        user.setEmail(userDTO.getEmail());
//        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        user.setPassword(userDTO.getPassword());
//        user.setAvatar(userDTO.getAvatar());
//        user.setBio(userDTO.getBio());
//        user.setCreatedAt(LocalDate.now());
//        user.setLastLogin(LocalDate.now());
//        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : Role.MEMBER);
//        user.setBanned(userDTO.isBanned());
//
//        User savedUser = repository.save(user);
//
//        return mapper.toDTO(savedUser);
//    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = repository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("user not found");
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setBio(dto.getBio());
        user.setAvatar(dto.getAvatar());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPassword(dto.getPassword());
        User savedUser = repository.save(user);
        return mapper.toDTO(savedUser);
    }

    public UserDTO updateUserRole(Long id, UserDTO dto){
        User user = repository.findById(id).orElse(null);
        if (user == null){
            throw new RuntimeException("user not found");
        }
        user.setRole(dto.getRole());
        User savedUser = repository.save(user);
        return mapper.toDTO(savedUser);
    }

    public UserDTO updateUserStatus(Long id, UserDTO dto){
        User user = repository.findById(id).orElse(null);
        if (user == null){
            throw new RuntimeException("user not found");
        }
        user.setBanned(dto.isBanned());
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
}
