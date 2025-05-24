package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

public interface Track extends PersistentObject {
    String getName();
    int getDurationSec();
    String getPath();
    Album getAlbum();
    boolean getIsFavorite();

    void setName(String name);
    void setDurationSec(int durationSec);
    void setPath(String path);
    void setAlbum(Album album);
    void setIsFavorite(boolean isFavorite);
}
