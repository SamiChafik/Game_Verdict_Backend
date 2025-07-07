package com.example.game_verdict.services;

import com.example.game_verdict.dtos.FavoriteGameDTO;
import com.example.game_verdict.entities.FavoriteGame;
import com.example.game_verdict.entities.Game;
import com.example.game_verdict.entities.Member;
import com.example.game_verdict.exceptions.ResourceNotFoundException;
import com.example.game_verdict.mappers.FavoriteGameMapper;
import com.example.game_verdict.repositories.FavoriteGameRepository;
import com.example.game_verdict.repositories.GameRepository;
import com.example.game_verdict.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteGameService {

    private final FavoriteGameRepository favoriteGameRepository;
    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;
    private final FavoriteGameMapper favoriteGameMapper;

    @Autowired
    public FavoriteGameService(FavoriteGameRepository favoriteGameRepository,
                               GameRepository gameRepository,
                               MemberRepository memberRepository,
                               FavoriteGameMapper favoriteGameMapper) {
        this.favoriteGameRepository = favoriteGameRepository;
        this.gameRepository = gameRepository;
        this.memberRepository = memberRepository;
        this.favoriteGameMapper = favoriteGameMapper;
    }

    @Transactional(readOnly = true)
    public List<FavoriteGameDTO> getAllFavorites() {
        List<FavoriteGame> favorites = favoriteGameRepository.findAll();
        return favoriteGameMapper.toDTOs(favorites);
    }

    @Transactional(readOnly = true)
    public List<FavoriteGameDTO> getFavoritesByUserId(Long userId) {
        List<FavoriteGame> favorites = favoriteGameRepository.findByUserId(userId);
        return favoriteGameMapper.toDTOs(favorites);
    }

    @Transactional(readOnly = true)
    public FavoriteGameDTO getFavoriteById(Long id) {
        FavoriteGame favorite = favoriteGameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found with id: " + id));
        return favoriteGameMapper.toDTO(favorite);
    }

    @Transactional
    public FavoriteGameDTO createFavorite(FavoriteGameDTO favoriteGameDTO) {
        Game game = gameRepository.findById(favoriteGameDTO.getGameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + favoriteGameDTO.getGameId()));

        Member member = memberRepository.findById(favoriteGameDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + favoriteGameDTO.getUserId()));

        if (favoriteGameRepository.existsByUserIdAndGameId(favoriteGameDTO.getUserId(), favoriteGameDTO.getGameId())) {
            throw new IllegalArgumentException("Game is already in favorites");
        }

        FavoriteGame favorite = new FavoriteGame();
        favorite.setUserId(favoriteGameDTO.getUserId());
        favorite.setGame(game);
        favorite.setMember(member);

        FavoriteGame savedFavorite = favoriteGameRepository.save(favorite);
        return favoriteGameMapper.toDTO(savedFavorite);
    }

    @Transactional
    public void deleteFavorite(Long id) {
        if (!favoriteGameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Favorite not found with id: " + id);
        }
        favoriteGameRepository.deleteById(id);
    }

    @Transactional
    public void deleteFavoriteByUserAndGame(Long userId, Long gameId) {
        FavoriteGame favorite = favoriteGameRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorite not found for user and game"));
        favoriteGameRepository.delete(favorite);
    }
}