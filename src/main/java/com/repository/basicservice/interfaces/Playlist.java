package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

import java.time.LocalDate;

public interface Playlist extends PersistentObject {
    String getName();
    LocalDate getCreationDate();

    void setName(String name);
    void setCreationDate(LocalDate creationDate);
}
