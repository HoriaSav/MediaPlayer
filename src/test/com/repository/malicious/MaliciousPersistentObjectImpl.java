package com.repository.malicious;

import com.repository.PersistentObject;

public class MaliciousPersistentObjectImpl implements PersistentObject {
    public long getObjectID() {
        return 0L;
    }

    public boolean isPersistent() {
        return false;
    }
}
