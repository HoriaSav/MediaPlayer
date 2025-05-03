package com.repository.basicservice.impl;

import com.repository.basicservice.AbstractPersistentJDBCObject;
import com.repository.basicservice.interfaces.Album;
import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.basicservice.interfaces.Track;
import com.repository.util.IDSequenceGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrackImpl extends AbstractPersistentJDBCObject implements Track {

    private String name;
    private int durationSec;
    private String path;
    private Album album;
    private boolean isFavorite;

    public TrackImpl(BasicDBService service, long id) {
        super(service, id);
    }

    public TrackImpl(BasicDBService service, String name, int durationSec, String path, Album album) {
        this(service, INVALID_OBJECT_ID);
        this.name = name;
        this.durationSec = durationSec;
        this.path = path;
        this.album = album;
        this.isFavorite = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDurationSec() {
        return durationSec;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Album getAlbum() {
        return album;
    }

    @Override
    public boolean getIsFavorite() {
        return isFavorite;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDurationSec(int durationSec) {
        this.durationSec = durationSec;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    private void insertTrack(Connection connection) throws SQLException {
        String sql = "INSERT INTO track (id, name, duration_sec, path, album_id, isfavorite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, getObjectID());
            ps.setString(2, name);
            ps.setInt(3, durationSec);
            ps.setString(4, path);
            ps.setLong(5, album.getObjectID());
            ps.setBoolean(6, isFavorite);
            ps.executeUpdate();
        }
    }
    /**
     * Updates an existing ward record in the database.
     *
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */

    private void updateTrack(Connection connection) throws SQLException {
        String sql = "UPDATE track SET name = ?, duration_sec = ?, path = ?, album_id = ?, isfavorite =? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, durationSec);
            ps.setString(3, path);
            ps.setLong(4, album.getObjectID());
            ps.setBoolean(5, isFavorite);
            ps.setLong(6, getObjectID());
            ps.executeUpdate();
        }
    }

    @Override
    public long store(Connection connection) throws SQLException {
        if (!isPersistent()) {
            setObjectID(IDSequenceGenerator.generateNewObjectID(connection, "track_id_seq"));
            insertTrack(connection);
        } else {
            updateTrack(connection);
        }

        return getObjectID();
    }
}
