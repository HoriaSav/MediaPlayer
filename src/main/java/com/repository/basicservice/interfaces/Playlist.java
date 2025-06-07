package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

import java.time.LocalDate;

/**
 * Represents a playlist, which is a persistent object in the repository.
 * A playlist has a name and a creation date.
 */
public interface Playlist extends PersistentObject {

    /**
     * Retrieves the name associated with this object.
     *
     * @return the name, never null
     */
    String getName();

    /**
     * Retrieves the creation date of the playlist.
     *
     * @return the creation date of the playlist as a LocalDate, never null
     */
    LocalDate getCreationDate();

    /**
     * Sets the name of the playlist.
     *
     * @param name the name to be set for the playlist; must be a non-null and non-empty string
     * @throws IllegalArgumentException if the name is null or empty
     */
    void setName(String name);

    /**
     * Sets the creation date of the playlist.
     *
     * @param creationDate the creation date to be set for the playlist; must not be null
     * @throws IllegalArgumentException if the creationDate is null
     */
    void setCreationDate(LocalDate creationDate);
}
