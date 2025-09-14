package com.example.game_verdict.controllers;

import com.example.game_verdict.dtos.GameDTO;
import com.example.game_verdict.services.FavoriteGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/favorites")
@CrossOrigin("*")
public class FavoriteGameController {

    private final FavoriteGameService favoriteGameService;

    @Autowired
    public FavoriteGameController(FavoriteGameService favoriteGameService) {
        this.favoriteGameService = favoriteGameService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<GameDTO>> getFavoritesByUser(Principal principal) {
        List<GameDTO> favorites = favoriteGameService.getFavoritesByPrincipal(principal);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/{gameId}")
    public ResponseEntity<Void> createFavorite(@PathVariable Long gameId, Principal principal) {
        favoriteGameService.createFavorite(gameId, principal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteFavoriteByUserAndGame(
            @PathVariable("gameId") Long gameId, Principal principal) {
        favoriteGameService.deleteFavoriteByPrincipalAndGame(principal, gameId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Boolean> isGameFavoritedByUser(
            @PathVariable("gameId") Long gameId, Principal principal) {
        boolean isFavorited = favoriteGameService.isGameFavoritedByPrincipal(principal, gameId);
        return ResponseEntity.ok(isFavorited);
    }
}