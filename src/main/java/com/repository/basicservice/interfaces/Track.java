package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

/**
 * Represents a track, which is a persistent object in the repository.
 * A track has a name, a duration (in seconds), a path, is associated with an album and has a liked status.
 */
public interface Track extends PersistentObject {
    /**
     * Retrieves the name of this track.
     *
     * @return the name of the track, never null
     */
    String getName();

    /**
     * Retrieves the duration of this track.
     *
     * @return the duration of the track in seconds, non-negative value
     * @throws IllegalArgumentException if the duration is negative
     */
    int getDurationSec();

    /**
     * Retrieves the file path associated with this track.
     *
     * @return the path of the track as a String, never null
     */
    String getPath();

    /**
     * Retrieves the album associated with this track.
     *
     * @return the album of the track, never null
     */
    Album getAlbum();

    /**
     * Retrieves the favorite status of this track.
     *
     * @return true if the track is marked as a favorite, false otherwise
     */
    boolean getIsFavorite();

    /**
     * Sets the name of the track.
     *
     * @param name the name of the track; must be a non-null and non-empty string
     * @throws IllegalArgumentException if the name is null or empty
     */
    void setName(String name);

    /**
     * Sets the duration of this track.
     *
     * @param durationSec the duration of the track in seconds
     */
    void setDurationSec(int durationSec);

    /**
     * Sets the file path associated with this track.
     *
     * @param path the file path of the track as a String. Must be non-null and valid.
     * @throws IllegalArgumentException if the path is null or empty
     */
    void setPath(String path);

    /**
     * Sets the album associated with this track.
     *
     * @param album the album to be associated with this track; must be a non-null instance of Album
     * @throws IllegalArgumentException if the album is null
     */
    void setAlbum(Album album);

    /**
     * Sets the favorite status of the track.
     *
     * @param isFavorite true to mark the track as a favorite, false otherwise
     */
    void setIsFavorite(boolean isFavorite);

    /**
     * Indicates whether this object is equal to another object.
     * The comparison should evaluate whether the specified object is logically equivalent
     * to this instance, based on relevant criteria. Typically, this involves comparing
     * the properties or fields that determine the object's identity.
     *
     * @param obj the object to compare for equality, may be null
     * @return true if the specified object is equal to this instance, false otherwise
     */
    boolean equals(Object obj);

    /**
     * Computes the hash code for this object. The hash code is generally calculated
     * based on the object's state or properties that are relevant for equality comparison.
     *
     * @return the hash code value of this object as an integer
     */
    int hashCode();
}