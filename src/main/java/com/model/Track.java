package com.model;

public class Track {
    private String name;
    private String artist;
    private String album;
    private int duration;
    private String path;

    public Track(String name, String artist, String album, int duration, String path){
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
        this.name = name;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
