package com.repository.basicservice.impl;

import com.repository.basicservice.AbstractPersistentJDBCObject;
import com.repository.basicservice.interfaces.Artist;
import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.util.IDSequenceGenerator;
import com.repository.util.InputValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtistImpl extends AbstractPersistentJDBCObject implements Artist {
    private String name;
    private String genre;

    public ArtistImpl(BasicDBService service, long id) {
        super(service, id);
    }

    public ArtistImpl(BasicDBService service, String name, String genre) {
        this(service, INVALID_OBJECT_ID);
        setName(name);
        setGenre(genre);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public void setName(String name) {
        InputValidator.validateString(name);
        this.name = name;
    }

    @Override
    public void setGenre(String genre) {
        InputValidator.validateString(genre);
        this.genre = genre;
    }

    private void insertArtist(Connection connection) throws SQLException {
        String sql = "INSERT INTO artist (id, name, genre) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, getObjectID());
            ps.setString(2, name);
            ps.setString(3, genre);
            ps.executeUpdate();
        }
    }
    /**
     * Updates an existing ward record in the database.
     *
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */

    private void updateArtist(Connection connection) throws SQLException {
        String sql = "UPDATE artist SET name = ?, genre = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, genre);
            ps.setLong(3, getObjectID());
            ps.executeUpdate();
        }
    }

    @Override
    public long store(Connection connection) throws SQLException {
        if (!isPersistent()) {
            setObjectID(IDSequenceGenerator.generateNewObjectID(connection, "artist_id_seq"));
            insertArtist(connection);
        } else {
            updateArtist(connection);
        }

        return getObjectID();
    }
}
