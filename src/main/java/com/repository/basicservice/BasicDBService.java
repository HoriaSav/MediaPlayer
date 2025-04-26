package com.repository.basicservice;

import com.repository.PDBGVersion;
import com.repository.PersistentObject;

@PDBGVersion
public interface BasicDBService {

    long store(PersistentObject var1);

    void close();
}
