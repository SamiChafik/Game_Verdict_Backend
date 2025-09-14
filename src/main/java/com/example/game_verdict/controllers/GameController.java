package com.example.game_verdict.controllers;

import com.example.game_verdict.dtos.GameDTO;
import com.example.game_verdict.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin("*")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MEMBER', 'MODERATOR')")
    @GetMapping
    public ResponseEntity<List<GameDTO>> getAllGames() {
        List<GameDTO> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MEMBER', 'MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long id) {
        GameDTO game = gameService.getGameById(id);
        return ResponseEntity.ok(game);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'REVIEWER', 'MEMBER', 'MODERATOR')")
    @GetMapping("/{id}/review-count")
    public ResponseEntity<Integer> getReviewCount(@PathVariable Long id) {
        int count = gameService.getReviewCount(id);
        return ResponseEntity.ok(count);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO gameDTO) {
        GameDTO createdGame = gameService.createGame(gameDTO);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> updateGame(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        GameDTO updatedGame = gameService.updateGame(id, gameDTO);
        return ResponseEntity.ok(updatedGame);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/platforms")
    public ResponseEntity<List<String>> getGamePlatforms(@PathVariable Long id) {
        List<String> platforms = gameService.getGamePlatforms(id);
        return ResponseEntity.ok(platforms);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/genres")
    public ResponseEntity<List<String>> getGameGenres(@PathVariable Long id) {
        List<String> genres = gameService.getGameGenres(id);
        return ResponseEntity.ok(genres);
    }    @GetMapping("/recent")
    public ResponseEntity<List<GameDTO>> getRecentGames() {
        List<GameDTO> games = gameService.getRecentGames(3);
        return ResponseEntity.ok(games);
    }
}