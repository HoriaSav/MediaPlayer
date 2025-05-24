package com.repository;

public abstract class AbstractPersistentObject implements PersistentObject {
    public static boolean isValidObjectID(long id) {
        return id > 0L && id != 0L;
    }

    public boolean isPersistent() {
        return isValidObjectID(this.getObjectID());
    }

    public String toString() {
        long var10000 = this.getObjectID();
        return "OID=" + var10000 + ":" + super.toString();
    }
}
