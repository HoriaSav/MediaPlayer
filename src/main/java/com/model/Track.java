package com.model;

import com.util.InputValidator;

public class Track {

    private String name;
    private String artist;
    private String album;
    private int duration;
    private String path;

    public Track(String name, String artist, String album, int duration, String path){
        InputValidator.validateTrack(name, artist, album, duration, path);
        setName(name);
        setArtist(artist);
        setAlbum(album);
        setDuration(duration);
        setPath(path);
    }

    public String getName() {
        return name;
    }
    public String getArtist() {
        return artist;
    }
    public String getAlbum() {
        return album;
    }
    public int getDuration() {
        return duration;
    }
    public String getPath() {
        return path;
    }

    public void setName(String name) {
        InputValidator.validateTrackName(name);
        this.name = name;
    }
    public void setArtist(String artist) {
        InputValidator.validateArtist(artist);
        this.artist = artist;
    }
    public void setAlbum(String album) {
        InputValidator.validateAlbum(album);
        this.album = album;
    }
    public void setDuration(int duration) {
        InputValidator.validateDuration(duration);
        this.duration = duration;
    }
    public void setPath(String path) {
        InputValidator.validatePath(path);
        this.path = path;
    }
}
