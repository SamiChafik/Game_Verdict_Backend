package com.example.game_verdict.controllers;

import com.example.game_verdict.dtos.FavoriteGameDTO;
import com.example.game_verdict.services.FavoriteGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteGameController {

    private final FavoriteGameService favoriteGameService;

    @Autowired
    public FavoriteGameController(FavoriteGameService favoriteGameService) {
        this.favoriteGameService = favoriteGameService;
    }

    @GetMapping
    public ResponseEntity<List<FavoriteGameDTO>> getAllFavorites() {
        List<FavoriteGameDTO> favorites = favoriteGameService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteGameDTO>> getFavoritesByUser(@PathVariable Long userId) {
        List<FavoriteGameDTO> favorites = favoriteGameService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteGameDTO> getFavoriteById(@PathVariable Long id) {
        FavoriteGameDTO favorite = favoriteGameService.getFavoriteById(id);
        return ResponseEntity.ok(favorite);
    }

    @PostMapping
    public ResponseEntity<FavoriteGameDTO> createFavorite(@RequestBody FavoriteGameDTO favoriteGameDTO) {
        FavoriteGameDTO createdFavorite = favoriteGameService.createFavorite(favoriteGameDTO);
        return new ResponseEntity<>(createdFavorite, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteGameService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}/game/{gameId}")
    public ResponseEntity<Void> deleteFavoriteByUserAndGame(
            @PathVariable Long userId,
            @PathVariable Long gameId) {
        favoriteGameService.deleteFavoriteByUserAndGame(userId, gameId);
        return ResponseEntity.noContent().build();
    }
}