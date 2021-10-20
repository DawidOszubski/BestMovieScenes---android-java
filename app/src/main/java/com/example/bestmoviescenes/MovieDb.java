package com.example.bestmoviescenes;

public class MovieDb {

    private String movieTitle;
    private int videoIcon;
    private String videoText;
    private String videoMainWords;
    private boolean isFavourite;
    private int favaouriteIcon;

    public MovieDb( String movieTitle, int videoIcon, String videoText, String videoMainWords, boolean isFavourite, int favaouriteIcon) {
        this.movieTitle = movieTitle;
        this.videoIcon = videoIcon;
        this.videoText = videoText;
        this.videoMainWords = videoMainWords;
        this.isFavourite = isFavourite;
        this.favaouriteIcon = favaouriteIcon;
    }


    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getVideoIcon() {
        return videoIcon;
    }

    public void setVideoIcon(int videoIcon) {
        this.videoIcon = videoIcon;
    }

    public String getVideoText() {
        return videoText;
    }

    public void setVideoText(String videoText) {
        this.videoText = videoText;
    }

    public String getVideoMainWords() {
        return videoMainWords;
    }

    public void setVideoMainWords(String videoMainWords) {
        this.videoMainWords = videoMainWords;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getFavaouriteIcon() {
        return favaouriteIcon;
    }

    public void setFavaouriteIcon(int favaouriteIcon) {
        this.favaouriteIcon = favaouriteIcon;
    }
}
