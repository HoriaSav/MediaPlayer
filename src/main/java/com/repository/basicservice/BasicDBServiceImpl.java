package com.repository.basicservice;

import com.repository.PersistentObject;
import com.repository.exception.StoreException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of the BasicDBService interface for database operations.
 */

public class BasicDBServiceImpl implements BasicDBService {
    private final Connection connection;

    public BasicDBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long store(PersistentObject persistentObject) {
        if (persistentObject == null) {
            throw new AssertionError("PersistentObject must not be null");
        }
        if (!(persistentObject instanceof AbstractPersistentJDBCObject)) {
            throw new StoreException("Object is not a valid JDBC object!");
        }
        try {
            return ((AbstractPersistentJDBCObject) persistentObject).store(connection);
        } catch (SQLException e) {
            throw new StoreException("Failed to store object", e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
//            throw new ServiceException("Failed to close connection", e);
        }
    }
}
