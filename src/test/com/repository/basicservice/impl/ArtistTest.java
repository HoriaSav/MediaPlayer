package com.repository.basicservice.impl;

import com.repository.basicservice.AbstractEntityTestCase;
import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.exception.ValidationException;
import com.repository.util.IDSequenceGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;

@DisplayName("ArtistImpl - Setters")
public class ArtistTest extends AbstractEntityTestCase {

    @ParameterizedTest
    @ValueSource(strings = {"Rock", "123", "!", "___?", "*^7"})
    @DisplayName("should accept valid name and genre")
    void shouldAcceptValidNameAndGenre(String validInput) {
        BasicDBService mockService = mock(BasicDBService.class);
        ArtistImpl artist = new ArtistImpl(mockService, 0L);

        assertDoesNotThrow(() -> artist.setName(validInput));
        assertDoesNotThrow(() -> artist.setGenre(validInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n", "\t", "   "})
    @DisplayName("should throw when name is blank")
    void shouldThrow_WhenNameIsBlank(String invalidInput) {
        BasicDBService mockService = mock(BasicDBService.class);
        ArtistImpl artist = new ArtistImpl(mockService, 0L);

        assertThrows(ValidationException.class, () -> artist.setName(invalidInput));
    }

    @Test
    @DisplayName("should throw when is null")
    void shouldThrow_WhenNameIsNull() {
        BasicDBService mockService = mock(BasicDBService.class);
        ArtistImpl artist = new ArtistImpl(mockService, 0L);

        assertThrows(ValidationException.class, () -> artist.setName(null));
        assertThrows(ValidationException.class, () -> artist.setGenre(null));
    }

    @Test
    @DisplayName("should insert artist when object is not persistent")
    void shouldInsert_WhenNotPersistent() throws SQLException {
        // Arrange
        BasicDBService mockService = mock(BasicDBService.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(startsWith("INSERT"))).thenReturn(mockStatement);

        // Spy to override isPersistent()
        ArtistImpl artist = spy(new ArtistImpl(mockService, 0L));
        doReturn(false).when(artist).isPersistent();

        // Mock the static method to return a fake ID
        try (MockedStatic<IDSequenceGenerator> mockedStatic = mockStatic(IDSequenceGenerator.class)) {
            mockedStatic.when(() ->
                    IDSequenceGenerator.generateNewObjectID(mockConnection, "artist_id_seq")
            ).thenReturn(42L);

            artist.setName("Radiohead");
            artist.setGenre("Alternative");

            // Precondition: ID is invalid
            assertEquals(0L, artist.getObjectID());

            // Act
            long result = artist.store(mockConnection);

            // Assert
            assertEquals(42L, result);                  // return value
            assertEquals(42L, artist.getObjectID());    // internal state updated
            verify(mockConnection).prepareStatement(startsWith("INSERT"));
            verify(mockStatement).executeUpdate();
        }
    }

    @Test
    @DisplayName("should update artist when object is persistent")
    void shouldUpdate_WhenPersistent() throws SQLException {
        // Arrange
        BasicDBService mockService = mock(BasicDBService.class);
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(startsWith("UPDATE"))).thenReturn(mockStatement);

        ArtistImpl artist = spy(new ArtistImpl(mockService, 99L));
        doReturn(true).when(artist).isPersistent();
        doReturn(99L).when(artist).getObjectID();

        artist.setName("Tool");
        artist.setGenre("Progressive");

        // Act
        long result = artist.store(mockConnection);

        // Assert
        assertEquals(99L, result);
        assertEquals(99L, artist.getObjectID());
        verify(mockConnection).prepareStatement(startsWith("UPDATE"));
        verify(mockStatement).executeUpdate();
    }

    @Test
    @DisplayName("should throw SQLException when insert fails")
    void shouldThrowSQLException_WhenInsertFails() throws SQLException {
        // Arrange
        BasicDBService mockService = mock(BasicDBService.class);
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.prepareStatement(startsWith("INSERT")))
                .thenThrow(new SQLException("DB insert failed"));

        ArtistImpl artist = spy(new ArtistImpl(mockService, 0L));
        doReturn(false).when(artist).isPersistent();

        try (MockedStatic<IDSequenceGenerator> mockedStatic = mockStatic(IDSequenceGenerator.class)) {
            mockedStatic.when(() ->
                    IDSequenceGenerator.generateNewObjectID(mockConnection, "artist_id_seq")
            ).thenReturn(42L);

            artist.setName("Muse");
            artist.setGenre("Alternative Rock");

            // Act & Assert
            SQLException ex = assertThrows(SQLException.class, () -> artist.store(mockConnection));
            assertEquals("DB insert failed", ex.getMessage());
        }
    }

    @Test
    @DisplayName("should throw SQLException when update fails")
    void shouldThrowSQLException_WhenUpdateFails() throws SQLException {
        // Arrange
        BasicDBService mockService = mock(BasicDBService.class);
        Connection mockConnection = mock(Connection.class);

        when(mockConnection.prepareStatement(startsWith("UPDATE")))
                .thenThrow(new SQLException("DB update failed"));

        ArtistImpl artist = spy(new ArtistImpl(mockService, 99L));
        doReturn(true).when(artist).isPersistent();

        artist.setName("Pink Floyd");
        artist.setGenre("Psychedelic Rock");

        // Act & Assert
        SQLException ex = assertThrows(SQLException.class, () -> artist.store(mockConnection));
        assertEquals("DB update failed", ex.getMessage());
    }
}
