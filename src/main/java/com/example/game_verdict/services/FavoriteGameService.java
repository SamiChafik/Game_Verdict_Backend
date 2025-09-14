package com.example.game_verdict.services;

import com.example.game_verdict.dtos.GameDTO;
import com.example.game_verdict.entities.FavoriteGame;
import com.example.game_verdict.entities.Game;
import com.example.game_verdict.entities.User;
import com.example.game_verdict.mappers.GameMapper;
import com.example.game_verdict.repositories.FavoriteGameRepository;
import com.example.game_verdict.repositories.GameRepository;
import com.example.game_verdict.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteGameService {

    private final FavoriteGameRepository favoriteGameRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Autowired
    public FavoriteGameService(FavoriteGameRepository favoriteGameRepository,
                               UserRepository userRepository,
                               GameRepository gameRepository,
                               GameMapper gameMapper) {
        this.favoriteGameRepository = favoriteGameRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public List<GameDTO> getFavoritesByPrincipal(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        List<FavoriteGame> favoriteGames = favoriteGameRepository.findByUser(user);
        List<Game> games = favoriteGames.stream().map(FavoriteGame::getGame).collect(Collectors.toList());
        return gameMapper.toDTOs(games);
    }

    public void createFavorite(Long gameId, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()) {
            Optional<FavoriteGame> existingFavorite = favoriteGameRepository
                    .findByUserAndGame(user, game.get());

            if (existingFavorite.isEmpty()) {
                FavoriteGame favoriteGame = new FavoriteGame();
                favoriteGame.setUser(user);
                favoriteGame.setGame(game.get());
                favoriteGameRepository.save(favoriteGame);
            }
        }
    }

    public void deleteFavoriteByPrincipalAndGame(Principal principal, Long gameId) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            favoriteGameRepository.findByUserAndGame(user, game.get())
                    .ifPresent(favoriteGameRepository::delete);
        }
    }

    public boolean isGameFavoritedByPrincipal(Principal principal, Long gameId) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        Optional<Game> game = gameRepository.findById(gameId);
        return game.isPresent() && favoriteGameRepository.findByUserAndGame(user, game.get()).isPresent();
    }
}
