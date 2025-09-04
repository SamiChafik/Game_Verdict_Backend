package com.example.game_verdict.services;

import com.example.game_verdict.dtos.GameDTO;
import com.example.game_verdict.entities.Game;
import com.example.game_verdict.exceptions.ResourceNotFoundException;
import com.example.game_verdict.mappers.GameMapper;
import com.example.game_verdict.repositories.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Transactional(readOnly = true)
    public List<GameDTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return gameMapper.toDTOs(games);
    }

    @Transactional(readOnly = true)
    public GameDTO getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));
        return gameMapper.toDTO(game);
    }

    @Transactional
    public GameDTO createGame(GameDTO gameDTO) {
        Game game = gameMapper.toEntity(gameDTO);
        game.setId(null);
        Game savedGame = gameRepository.save(game);
        return gameMapper.toDTO(savedGame);
    }

    @Transactional
    public GameDTO updateGame(Long id, GameDTO gameDTO) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));

        existingGame.setTitle(gameDTO.getTitle());
        existingGame.setDescription(gameDTO.getDescription());
        existingGame.setReleaseDate(gameDTO.getReleaseDate());
        existingGame.setLink(gameDTO.getLink());
        existingGame.setCoverImg(gameDTO.getCoverImg());
        existingGame.setRating(gameDTO.getRating());
        existingGame.setAverageRating(gameDTO.getAverageRating());
        existingGame.setRawgId(gameDTO.getRawgId());

        existingGame.getPlatforms().clear();
        if (gameDTO.getPlatforms() != null) {
            existingGame.getPlatforms().addAll(gameDTO.getPlatforms());
        }

        existingGame.getGenres().clear();
        if (gameDTO.getGenres() != null) {
            existingGame.getGenres().addAll(gameDTO.getGenres());
        }

        Game updatedGame = gameRepository.save(existingGame);
        return gameMapper.toDTO(updatedGame);
    }

    @Transactional
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Game not found with id: " + id);
        }
        gameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public int getReviewCount(Long gameId) {
        return gameRepository.findById(gameId)
                .map(game -> game.getReviews().size())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameId));
    }

    @Transactional(readOnly = true)
    public List<String> getGamePlatforms(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameId));
        return game.getPlatforms();
    }

    @Transactional(readOnly = true)
    public List<String> getGameGenres(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameId));
        return game.getGenres();
    }
}