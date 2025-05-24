package com.model;

public class Settings {
    private int volume;
    //TODO: implement theme selection with enum

    //TODO: add settings storage

    private Settings(int volume) {
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
