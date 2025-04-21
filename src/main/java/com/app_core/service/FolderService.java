package com.app_core.service;

import com.app_core.utils.Folder;
import com.app_core.utils.Track;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderService {
    private final List<Folder> folderList;
    private Folder currentFolder;

    public FolderService() {
        this.folderList = new ArrayList<>();
    }

    public void addMusicFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Music Folder");
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        
        if (selectedDirectory != null) {
            Folder folder = new Folder(selectedDirectory);
            folderList.add(folder);
            if (currentFolder == null) {
                currentFolder = folder;
            }
        }
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(String folderName) {
        for (Folder folder : folderList) {
            if (folder.getFolderName().equals(folderName)) {
                this.currentFolder = folder;
                return;
            }
        }
    }

    public List<Track> getCurrentFolderTracks() {
        return currentFolder != null ? currentFolder.getMusicFiles() : new ArrayList<>();
    }
}