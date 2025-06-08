package com.repository.basicservice.impl;

import com.repository.basicservice.AbstractPersistentJDBCObject;
import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.basicservice.interfaces.Playlist;
import com.repository.basicservice.interfaces.PlaylistTrack;
import com.repository.basicservice.interfaces.Track;
import com.repository.util.IDSequenceGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlaylistTrackImpl extends AbstractPersistentJDBCObject implements PlaylistTrack {

    private Playlist playlist;
    private Track track;
    private int trackPlaylistNumber;

    protected PlaylistTrackImpl(BasicDBService service, long id) {
        super(service, id);
    }

    public PlaylistTrackImpl(BasicDBService service, Playlist playlist, Track track, int trackPlaylistNumber) {
        this(service, INVALID_OBJECT_ID);
        this.playlist = playlist;
        this.track = track;
        this.trackPlaylistNumber = trackPlaylistNumber;
    }

    @Override
    public Playlist getPlaylist() {
        return playlist;
    }

    @Override
    public Track getTrack() {
        return track;
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public void setTrack(Track track) {
        this.track = track;
    }
    
    private void insertPlaylistTrack (Connection connection) throws SQLException {
        String sql = "INSERT INTO playlist_track (playlist_id, track_id, trackplaylistnumber) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, playlist.getObjectID());
            ps.setLong(2, track.getObjectID());
            ps.setLong(3, trackPlaylistNumber);
            ps.executeUpdate();
        }
    }
    /**
     * Updates an existing ward record in the database.
     *
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */

    private void updatePlaylistTrack (Connection connection) throws SQLException {
        String sql = "UPDATE playlist_track SET trackplaylistnumber = ? WHERE playlist_id = ? AND track_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, trackPlaylistNumber);
            ps.setLong(2, playlist.getObjectID());
            ps.setLong(3, track.getObjectID());
            ps.executeUpdate();
        }
    }

    @Override
    public long store(Connection connection) throws SQLException {
        if (!isPersistent()) {
            setObjectID(IDSequenceGenerator.generatePostgresSequenceId(connection, "playlist_track_id_seq"));
            insertPlaylistTrack(connection);
        } else {
            updatePlaylistTrack(connection);
        }

        return getObjectID();
    }
}
