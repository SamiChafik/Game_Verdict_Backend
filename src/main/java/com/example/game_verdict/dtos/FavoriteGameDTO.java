package com.example.game_verdict.dtos;

public class FavoriteGameDTO {
    private Long id;
    private Long userId;
    private Long gameId;

    public FavoriteGameDTO() {}

    public FavoriteGameDTO(Long id, Long userId, Long gameId) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}