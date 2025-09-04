package com.example.game_verdict.controllers;

import com.example.game_verdict.services.UserService;
import com.example.game_verdict.dtos.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

//    @PostMapping
//    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
//        UserDTO createdUser = service.createUser(userDTO);
//        return ResponseEntity.ok(createdUser);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/userList")
    public List<UserDTO> getUsers(){
        return service.getUsersList();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'SENDER')")
    @PutMapping("/updateUser/{id}")
    public UserDTO updateUser(@RequestBody UserDTO dto, @PathVariable Long id){
        return service.updateUser(id,dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRole/{id}")
    public UserDTO updateUserRole(@RequestBody UserDTO dto, @PathVariable Long id){
        return service.updateUserRole(id,dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PutMapping("/updateStatus/{id}")
    public UserDTO updateUserStatus(@RequestBody boolean isBanned, @PathVariable Long id){
        return service.updateUserStatus(id, isBanned);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id){
        service.deleteUser(id);
    }

}
