package com.repository.basicservice.interfaces;

import com.repository.PersistentObject;

public interface PlaylistTrack extends PersistentObject {
    Playlist getPlaylist();
    Track getTrack();
    int getTrackPlaylistNumber();

    void setPlaylist(Playlist playlist);
    void setTrack(Track track);
    void setTrackPlaylistNumber(int trackPlaylistNumber);
}
