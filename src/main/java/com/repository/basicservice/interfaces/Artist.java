package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

public interface Artist extends PersistentObject {
    String getName();
    String getGenre();

    void setName(String name);
    void setGenre(String genre);
}
