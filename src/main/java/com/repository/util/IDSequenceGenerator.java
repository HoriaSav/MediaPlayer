package com.repository.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.repository.PersistentObject.INVALID_OBJECT_ID;

public class IDSequenceGenerator {

    public static long generateNewObjectID(Connection connection, String seqID) throws SQLException {
        String sql = "SELECT nextval('" + seqID + "')";
        long id = INVALID_OBJECT_ID;
        try (PreparedStatement psID = connection.prepareStatement(sql)) {
            ResultSet rsId = psID.executeQuery();
            if (rsId.next()) {
                id = rsId.getLong(1);
            }
        }
        return id;
    }
}
