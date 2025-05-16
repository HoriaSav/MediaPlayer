package com.repository.basicservice.util;

import com.repository.basicservice.AbstractEntityTestCase;
import com.repository.basicservice.interfaces.Artist;
import com.repository.exception.ValidationException;
import com.repository.util.InputValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InputValidatorTest")
public class InputValidatorTest extends AbstractEntityTestCase {
    @Nested
    @DisplayName("validateStringName")
    class ValidateStringNameTests {

        @Test
        @DisplayName("should pass when name is valid")
        void shouldPass_WhenNameIsValid() {
            assertDoesNotThrow(() -> InputValidator.validateStringName("Bohemian Rhapsody"));
        }

        @Test
        @DisplayName("should throw when name is null")
        void shouldThrow_WhenNameIsNull() {
            assertThrows(ValidationException.class, () -> InputValidator.validateStringName(null));
        }

        @ParameterizedTest
        @ValueSource(strings = { "", "   " })
        @DisplayName("should throw when name is empty or blank")
        void shouldThrow_WhenNameIsEmptyOrBlank(String input) {
            assertThrows(ValidationException.class, () -> InputValidator.validateStringName(input));
        }

        @Test
        @DisplayName("should throw when name exceeds 255 characters")
        void shouldThrow_WhenNameIsTooLong() {
            String longName = "A".repeat(256);
            assertThrows(ValidationException.class, () -> InputValidator.validateStringName(longName));
        }
    }

    @Nested
    @DisplayName("validateDurationInSeconds")
    class ValidateDurationInSecondsTests {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 300, 43200})
        @DisplayName("should pass when duration is valid")
        void shouldPass_WhenDurationIsValid(int validDuration) {
            assertDoesNotThrow(() -> InputValidator.validateDuration(validDuration));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -100})
        @DisplayName("should throw when duration is negative")
        void shouldThrow_WhenDurationIsNegative(int invalidDuration) {
            assertThrows(ValidationException.class, () -> InputValidator.validateDuration(invalidDuration));
        }

        @Test
        @DisplayName("should throw when duration is greater than 43200")
        void shouldThrow_WhenDurationIsTooLong() {
            assertThrows(ValidationException.class, () -> InputValidator.validateDuration(43201));
        }
    }

    @Nested
    @DisplayName("validatePath")
    class ValidatePathTests {

        @Test
        @DisplayName("should pass when path is valid and exists")
        void shouldPass_WhenPathExists(@TempDir Path tempDir) throws IOException {
            Path tempFile = tempDir.resolve("test.mp3");
            java.nio.file.Files.createFile(tempFile);

            assertDoesNotThrow(() -> InputValidator.validatePath(tempFile.toString()));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        @DisplayName("should throw when path is empty or blank")
        void shouldThrow_WhenPathIsEmpty(String path) {
            assertThrows(ValidationException.class, () -> InputValidator.validatePath(path));
        }

        @Test
        @DisplayName("should throw when path is null")
        void shouldThrow_WhenPathIsNull() {
            assertThrows(ValidationException.class, () -> InputValidator.validatePath(null));
        }

        @Test
        @DisplayName("should throw when file does not exist")
        void shouldThrow_WhenFileDoesNotExist(@TempDir Path tempDir) {
            Path nonexistentFile = tempDir.resolve("ghost.mp3");
            assertFalse(nonexistentFile.toFile().exists()); // confirm it's not created

            assertThrows(ValidationException.class, () -> InputValidator.validatePath(nonexistentFile.toString()));
        }
    }

    //TODO: must finish implementation of tests
}
