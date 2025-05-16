package com.repository.basicservice;

import com.repository.basicservice.interfaces.BasicDBService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractServiceTestCase {
    protected static BasicDBService service;

    @BeforeAll
    public static void beforeClass() {
        service = BasicDBServiceFactory.createBasicDBService();
    }

    @AfterAll
    public static void afterClass() {
        if (service != null) {
            service.close();
        } else {
            Assertions.fail("Instance of BasicDBService corrupted! This should not happen! Call your local DB2 admin.");
        }

    }
}
