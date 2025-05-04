package com.repository.basicservice.impl;

import com.repository.basicservice.AbstractPersistentJDBCObject;
import com.repository.basicservice.interfaces.Album;
import com.repository.basicservice.interfaces.Artist;
import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.util.IDSequenceGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlbumImpl extends AbstractPersistentJDBCObject implements Album {
    private String name;
    private Artist artist;

    public AlbumImpl(BasicDBService service, long id) {
        super(service, id);
    }

    public AlbumImpl(BasicDBService service, String name, Artist artist) {
        this(service, INVALID_OBJECT_ID);
        this.name = name;
        this.artist = artist;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Artist getArtist() {
        return artist;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    private void insertAlbum(Connection connection) throws SQLException {
        String sql = "INSERT INTO album (id, name, artist_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, getObjectID());
            ps.setString(2, name);
            ps.setLong(3, artist.getObjectID());
            ps.executeUpdate();
        }
    }
    /**
     * Updates an existing ward record in the database.
     *
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */

    private void updateAlbum(Connection connection) throws SQLException {
        String sql = "UPDATE album SET name = ?, artist_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setLong(2, artist.getObjectID());
            ps.setLong(3, getObjectID());
            ps.executeUpdate();
        }
    }

    @Override
    public long store(Connection connection) throws SQLException {
        if (!isPersistent()) {
            setObjectID(IDSequenceGenerator.generateNewObjectID(connection, "ward_id_seq"));
            insertAlbum(connection);
        } else {
            updateAlbum(connection);
        }

        return getObjectID();
    }
}
