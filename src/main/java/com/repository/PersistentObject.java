package com.repository;

@PDBGVersion
public interface PersistentObject {
    long INVALID_OBJECT_ID = 0L;

    long getObjectID();

    boolean isPersistent();
}
