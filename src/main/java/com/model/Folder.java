package com.model;

import com.repository.basicservice.interfaces.Track;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private final File folderFile;
    private final List<Track> musicFiles;
    private final String name;

    public Folder(File folderFile) {
        this.folderFile = folderFile;
        this.name = folderFile.getName();
        this.musicFiles = new ArrayList<>();
    }

    public void addMusicFile(Track track) {
        musicFiles.add(track);
    }

    public List<Track> getMusicFiles() {
        return musicFiles;
    }

    public String getFolderName() {
        return name;
    }

    public File getFolderFile() {
        return folderFile;
    }
}