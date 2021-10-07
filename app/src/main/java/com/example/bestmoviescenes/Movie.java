package com.example.bestmoviescenes;

import java.util.ArrayList;

public class Movie {

    int backgroundImage;
    String title;

    public Movie(int backgroundImage, String title) {
        this.backgroundImage = backgroundImage;
        this.title = title;
    }

    public int getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(int backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
