//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.repository.basicservice;

import com.repository.AbstractPersistentObject;
import com.repository.basicservice.BasicDBService;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractPersistentJDBCObject extends AbstractPersistentObject {
    private long objectID;
    private final BasicDBService service;

    protected AbstractPersistentJDBCObject(BasicDBService service, long id) {
        assert service != null;

        this.service = service;
        this.objectID = id;
    }

    protected void setObjectID(long ID) {
        this.objectID = ID;
    }

    public long getObjectID() {
        return this.objectID;
    }

    protected BasicDBService getBasicDBService() {
        return this.service;
    }

    public abstract long store(Connection var1) throws SQLException;
}
