package com.example.uasmcs;

import java.io.Serializable;

public class GameModel implements Serializable {

    private String gameId;
    private String title;
    private double normalPrice;
    private String thumb;
    private double steamRating;

    private boolean isInLibrary;

    public GameModel(String gameId, String title, double normalPrice, String thumb, double steamRating) {
        this.gameId = gameId;
        this.title = title;
        this.normalPrice = normalPrice;
        this.thumb = thumb;
        this.steamRating = steamRating;

    }


    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getNormalPrice() {
        return normalPrice;
    }
    public void setNormalPrice(double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getThumb() {
        return thumb;
    }
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public double getSteamRating() {
        return steamRating;
    }
    public void setSteamRating(double steamRating) {
        this.steamRating = steamRating;
    }

    public boolean isInLibrary() {
        return isInLibrary;
    }

    public void setInLibrary(boolean inLibrary) {
        isInLibrary = inLibrary;
    }
}

