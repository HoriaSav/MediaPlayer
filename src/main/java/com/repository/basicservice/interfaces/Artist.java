package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

/**
 * Represents an artist, which is a persistent object in the repository.
 * An artist has a name and a genre.
 */
public interface Artist extends PersistentObject {
    /**
     * Returns the name of the artist.
     *
     * @return the name of the artist
     */
    String getName();

    /**
     * Retrieves the genre associated with the artist.
     *
     * @return the genre of the artist
     */
    String getGenre();

    /**
     * Sets the name of the artist.
     *
     * @param name the name to be set for the artist
     * @throws IllegalArgumentException if the name is null or invalid
     */
    void setName(String name);

    /**
     * Sets the genre of the artist.
     *
     * @param genre the genre to be set for the artist; must be a non-null and valid string
     * @throws IllegalArgumentException if the genre is null or invalid
     */
    void setGenre(String genre);
}
