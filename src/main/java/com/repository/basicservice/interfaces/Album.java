package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

/**
 * Represents an album, which is a persistent object in the repository.
 * An album has a name and is associated with an artist.
 */
public interface Album extends PersistentObject {
    /**
     * Returns the name of the album.
     *
     * @return the name of the album, never null
     */
    String getName();
    /**
     * Retrieves the artist associated with the album.
     *
     * @return the artist of the album, never null
     */
    Artist getArtist();

    /**
     * Sets the name of the album.
     *
     * @param name the name of the album
     * @throws IllegalArgumentException if the name is empty or null.
     */
    void setName(String name);
    /**
     * Sets the artist of the album.
     *
     * @param artist the artist of the album
     * @throws IllegalArgumentException if the artist is null.
     */
    void setArtist(Artist artist);
}
