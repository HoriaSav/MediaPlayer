package com.repository.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Utility class for providing a HikariCP-backed database connection pool.
 *
 * <p>This class initializes a {@link HikariDataSource} based on PostgreSQL connection settings.
 * It exposes a single static method to retrieve pooled connections.</p>
 */
public class DataSourceFactory {

    /** HikariCP configuration object. */
    private static final HikariConfig config = new HikariConfig();

    /** Shared HikariCP data source (connection pool). */
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/MediaPlayerDb");
        config.setUsername("postgres");
        config.setPassword("admin");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private DataSourceFactory() {}


    /**
     * Returns a connection from the HikariCP connection pool.
     *
     * @return a {@link Connection} to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static String getJdbcUrl() {
        return config.getJdbcUrl();
    }

    public static String getUsername() {
        return config.getUsername();
    }

    public static String getPassword() {
        return config.getPassword();
    }
}
