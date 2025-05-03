package com.repository.basicservice;

import com.repository.PersistentObject;
import com.repository.exception.StoreException;
import com.repository.malicious.MaliciousPersistentObjectImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServiceTestCase extends AbstractServiceTestCase {
    @Test
    public void testService() {
        Assertions.assertNotNull(service, "BasicDBService isn't available yet.");
    }

    @Test
    public void testStoreNull() {
        Assertions.assertThrows(AssertionError.class, () -> service.store((PersistentObject)null));
    }

    @Test
    public void testMaliciousPersistentObjectImpl() {
        Assertions.assertThrows(StoreException.class, () -> service.store(new MaliciousPersistentObjectImpl()));
    }
}
