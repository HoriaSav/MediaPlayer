package com.repository.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.repository.PersistentObject.INVALID_OBJECT_ID;

/**
 * Utility class for generating sequential IDs using PostgreSQL sequences.
 */
public final class IDSequenceGenerator {

    private static final int POSTGRESQL_MAX_IDENTIFIER_LENGTH = 63;
    private static final String SEQUENCE_NAME_PATTERN = "^[a-zA-Z_][a-zA-Z0-9_$]*$";
    private static final String NEXTVAL_SQL_TEMPLATE = "SELECT nextval(?)";

    // Prevent instantiation
    private IDSequenceGenerator() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Generates a new object ID using PostgreSQL sequence.
     *
     * @param connection   Database connection
     * @param sequenceName PostgreSQL sequence name
     * @return Generated sequence value or INVALID_OBJECT_ID if generation fails
     * @throws SQLException             if sequence is invalid or database error occurs
     * @throws IllegalArgumentException if the connection is null
     */
    public static long generatePostgresSequenceId(Connection connection, String sequenceName)
            throws SQLException {
        if (connection == null) {
            throw new IllegalArgumentException("Database connection cannot be null");
        }

        if (!isValidSequenceName(sequenceName)) {
            throw new SQLException("Invalid PostgreSQL sequence name: " + sequenceName);
        }

        long id = INVALID_OBJECT_ID;
        try (PreparedStatement psId = connection.prepareStatement(NEXTVAL_SQL_TEMPLATE)) {
            // Properly quote and escape the sequence name for PostgreSQL
            psId.setString(1, '"' + sequenceName + '"');
            try (ResultSet rsId = psId.executeQuery()) {
                if (rsId.next()) {
                    id = rsId.getLong(1);
                }
            }
        }
        return id;
    }

    private static boolean isValidSequenceName(String sequenceName) {
        return sequenceName != null &&
                sequenceName.matches(SEQUENCE_NAME_PATTERN) &&
                sequenceName.length() <= POSTGRESQL_MAX_IDENTIFIER_LENGTH;
    }
}