package com.repository.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.repository.PersistentObject.INVALID_OBJECT_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("IDSequenceGeneratorTest")
public class IDSequenceGeneratorTest {
    @Test
    @DisplayName("should return generated ID when result exists")
    void shouldReturnGeneratedID_WhenResultExists() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        String sequence = "my_sequence";
        long expectedId = 12345L;

        when(mockConnection.prepareStatement("SELECT nextval('" + sequence + "')")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getLong(1)).thenReturn(expectedId);

        long actualId = IDSequenceGenerator.generateNewObjectID(mockConnection, sequence);

        assertEquals(expectedId, actualId);
        verify(mockPreparedStatement).close();
    }

    @Test
    @DisplayName("should return INVALID_OBJECT_ID when result is empty")
    void shouldReturnInvalidObjectID_WhenResultIsEmpty() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        String sequence = "my_sequence";

        when(mockConnection.prepareStatement("SELECT nextval('" + sequence + "')")).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);  // no result

        long actualId = IDSequenceGenerator.generateNewObjectID(mockConnection, sequence);

        assertEquals(INVALID_OBJECT_ID, actualId);
        verify(mockPreparedStatement).close();
    }

    @Test
    @DisplayName("should propagate SQLException")
    void shouldThrowSQLException_WhenQueryFails() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        String sequence = "bad_sequence";

        when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("DB error"));

        assertThrows(SQLException.class, () -> IDSequenceGenerator.generateNewObjectID(mockConnection, sequence));
    }
}
