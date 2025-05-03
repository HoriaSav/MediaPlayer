package com.repository.basicservice.impl;

import com.repository.basicservice.AbstractPersistentJDBCObject;
import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.basicservice.interfaces.Playlist;
import com.repository.util.IDSequenceGenerator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class PlaylistImpl extends AbstractPersistentJDBCObject implements Playlist {

    private String name;
    private LocalDate creationDate;

    protected PlaylistImpl(BasicDBService service, long id) {
        super(service, id);
    }

    public PlaylistImpl(BasicDBService service, String name) {
        this(service, INVALID_OBJECT_ID);
        this.name = name;
        this.creationDate = LocalDate.now();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    private void insertPlaylist(Connection connection) throws SQLException {
        String sql = "INSERT INTO playlist (id, name, creation_date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, getObjectID());
            ps.setString(2, name);
            ps.setDate(3, Date.valueOf(creationDate));
            ps.executeUpdate();
        }
    }
    /**
     * Updates an existing ward record in the database.
     *
     * @param connection the database connection
     * @throws SQLException if a database access error occurs
     */

    private void updatePlaylist(Connection connection) throws SQLException {
        String sql = "UPDATE playlist SET name = ?, creation_date = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDate(2, Date.valueOf(creationDate));
            ps.setLong(3, getObjectID());
            ps.executeUpdate();
        }
    }

    @Override
    public long store(Connection connection) throws SQLException {
        if (!isPersistent()) {
            setObjectID(IDSequenceGenerator.generateNewObjectID(connection, "ward_id_seq"));
            insertPlaylist(connection);
        } else {
            updatePlaylist(connection);
        }

        return getObjectID();
    }
}
