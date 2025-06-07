package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

/**
 * Represents a track that belongs to a playlist. This interface provides methods
 * to manage the association between a track and a playlist, as well as the track's
 * position or order within the playlist. A PlaylistTrack is a persistent object and
 * can be stored in or retrieved from a repository.
 */
public interface PlaylistTrack extends PersistentObject {
    /**
     * Retrieves the playlist associated with this PlaylistTrack.
     *
     * @return the associated Playlist object, never null
     */
    Playlist getPlaylist();

    /**
     * Retrieves the track associated with this PlaylistTrack.
     *
     * @return the associated Track object, never null
     */
    Track getTrack();

    /**
     * Associates the specified playlist with this PlaylistTrack.
     *
     * @param playlist the playlist to be associated with this PlaylistTrack; must not be null
     * @throws IllegalArgumentException if the playlist is null
     */
    void setPlaylist(Playlist playlist);

    /**
     * Associates a specified track with this PlaylistTrack.
     *
     * @param track the track to be associated with this PlaylistTrack; must not be null
     * @throws IllegalArgumentException if the track is null
     */
    void setTrack(Track track);
}
