package com.example.game_verdict.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteGameDTO {
    private Long id;
    private Long userId;
    private Long gameId;
    private String gameTitle;
    private String gameCoverImg;

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

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameCoverImg() {
        return gameCoverImg;
    }

    public void setGameCoverImg(String gameCoverImg) {
        this.gameCoverImg = gameCoverImg;
    }
}