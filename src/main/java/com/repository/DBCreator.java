package com.repository;

import com.repository.exception.ServiceException;
import com.repository.schema.SchemaGenerator;
import com.repository.util.SchemaParser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public final class DBCreator implements SchemaGenerator {

    /**
     * JDBC URL for connecting to the PostgreSQL database.
     * Example: {@code "jdbc:postgresql://localhost:5432/mydb"}
     */
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/MediaPlayerDb";

    /** Username for the PostgreSQL database connection. */
    private static final String DB_USER = "postgres";

    /** Password for the PostgreSQL database connection. */
    private static final String DB_PASSWORD = "admin";


    /**
     * Main method used to initiate the database schema creation process.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        if (new DBCreator().createDatabase(JDBC_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Database created successfully");
        } else {
            System.out.println("Database creation failed");
        }
    }

    /**
     * Creates the database schema by connecting to the database and executing the defined DDL statements.
     *
     * @param jdbcUrl  the JDBC connection URL
     * @param user     the database username
     * @param password the database password
     * @return {@code true} if the schema was created successfully, {@code false} otherwise
     * @throws IllegalArgumentException if any input parameter is null or empty
     */
    public boolean createDatabase(String jdbcUrl, String user, String password) {
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            throw new IllegalArgumentException("jdbcUrl must not be null or empty");
        }
        if (user == null || user.isEmpty()) {
            throw new IllegalArgumentException("user must not be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password must not be null or empty");
        }

        try (Connection connection = createConnection(jdbcUrl, user, password); Statement statement = connection.createStatement()) {
            try {

                List<String> ddlStatements = SchemaParser.parseSchema("schema.sql");

                for (String ddl : ddlStatements) {
                    statement.execute(ddl);
                }

                System.out.println("Database schema created successfully!");
                return true;
            } catch (SQLException e) {
                throw new ServiceException("Failed to create database", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServiceException("Failed to close connection", e);
        }
    }

    /**
     * Establishes a connection to the PostgreSQL database using the given JDBC URL, username, and password.
     *
     * @param jdbcURL the JDBC connection URL
     * @param user    the database username
     * @param password the database password
     * @return a {@link Connection} object to the database
     * @throws ClassNotFoundException if the PostgreSQL driver class is not found
     * @throws SQLException if a database access error occurs
     */
    private Connection createConnection(String jdbcURL, String user, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(jdbcURL, user, password);
    }
}
