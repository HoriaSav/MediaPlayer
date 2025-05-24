package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

public interface Album extends PersistentObject {
    String getName();
    Artist getArtist();

    void setName(String name);
    void setArtist(Artist artist);
}
