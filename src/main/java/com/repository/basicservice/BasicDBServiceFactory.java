package com.repository.basicservice;

import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.util.DataSourceFactory;

import java.sql.Connection;

/**
 * {@link BasicDBServiceFactory} define a static factory method in order to create an instance of a
 * {@link BasicDBService} object.
 *
 * @version 1.0
 */
public class BasicDBServiceFactory {
    /**
     * Factory method in order to create an instance of a {@link BasicDBService} object.
     *
     * @return instance of a {@link BasicDBService} object
     */
    public static BasicDBService createBasicDBService() {
        try {
            Connection connection = DataSourceFactory.getConnection();
            return new BasicDBServiceImpl(connection);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create BasicDBService", e);
        }
    }
}
