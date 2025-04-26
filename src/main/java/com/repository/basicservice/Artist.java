package com.repository.basicservice;

import com.repository.AbstractPersistentObject;
import com.repository.PersistentObject;

public class Artist extends AbstractPersistentObject implements PersistentObject {

    private String name;
    private String genre;

    @Override
    public long getObjectID() {
        return 0;
    }
}
