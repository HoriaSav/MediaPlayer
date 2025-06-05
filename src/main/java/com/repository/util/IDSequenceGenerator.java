package com.repository.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.repository.PersistentObject.INVALID_OBJECT_ID;

public class IDSequenceGenerator {

    public static long generateNewObjectID(Connection connection, String seqID) throws SQLException {
        // Validate sequence name to prevent SQL injection
        if (!isValidSequenceName(seqID)) {
            throw new SQLException("Invalid sequence name: " + seqID);
        }
        
        // Use string format for the identifier, but only after validation
        String sql = String.format("SELECT nextval('%s')", seqID);
        long id = INVALID_OBJECT_ID;
        
        try (PreparedStatement psID = connection.prepareStatement(sql)) {
            ResultSet rsId = psID.executeQuery();
            if (rsId.next()) {
                id = rsId.getLong(1);
            }
        }
        return id;
    }

    private static boolean isValidSequenceName(String sequenceName) {
        // Allow only valid PostgreSQL identifier characters
        // PostgreSQL identifiers can contain letters, numbers, and underscores
        // This is a basic validation and can be enhanced based on your needs
        return sequenceName != null && 
               sequenceName.matches("^[a-zA-Z_][a-zA-Z0-9_$]*$") && 
               sequenceName.length() <= 63;  // PostgreSQL's identifier length limit
    }
}