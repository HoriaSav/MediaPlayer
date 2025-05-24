package com.service;

import com.exception.AudioFileProcessingException;
import com.exception.FolderOperationException;
import com.exception.TrackNotFoundException;
import com.model.Folder;
import com.model.Track;
import com.util.FileInfoExtractor;
import com.util.InputValidator;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FolderService {
    private final List<Folder> folderList;
    private Folder currentFolder;

    public FolderService() {
        this.folderList = new ArrayList<>();
    }

    public void addMusicFolder() throws FolderOperationException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Music Folder");
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            try {
                InputValidator.validateFolder(selectedDirectory);
                Folder folder = new Folder(selectedDirectory);
                setFolder(folder);
                folderList.add(folder);
                currentFolder = folder;
            } catch (Exception e) {
                throw new FolderOperationException("Failed to create folder from directory: " + 
                selectedDirectory.getPath(), e);
            }
        } else {
            throw new FolderOperationException("No directory selected");
        }
    }

    private void setFolder(Folder folder) {
        File[] files = folder.getFolderFile().listFiles();
        if (files == null) {
            throw new FolderOperationException("Unable to list files in folder: " + folder.getFolderFile().getPath());
        }

        for (File file : files) {
            if (isMusicFile(file)) {
                try {
                    processAudioFile(folder, file);
                } catch (CannotReadException | TagException | InvalidAudioFrameException |
                         ReadOnlyFileException | IOException e) {
                    throw new AudioFileProcessingException(
                            "Failed to process audio file: " + file.getName(), e);
                }
            }
        }
    }

    private void processAudioFile(Folder folder, File file) throws CannotReadException, TagException,
            InvalidAudioFrameException, ReadOnlyFileException, IOException {
        String name = FileInfoExtractor.getTrackTitle(file);
        String artist = FileInfoExtractor.getArtistName(file);
        String album = FileInfoExtractor.getAlbumTitle(file);
        int duration = FileInfoExtractor.getTrackDuration(file);
        String path = file.getPath();

        try {
            Track track = new Track(name, artist, album, duration, path);
            folder.addMusicFile(track);
        } catch (Exception e) {
            throw new AudioFileProcessingException(
                    "Invalid track data in file: " + file.getName() + " " + e.getMessage());
        }
    }

    private boolean isMusicFile(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }

        String name = file.getName().toLowerCase();
        return name.endsWith(".mp3") || name.endsWith(".wav");
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public void setCurrentFolder(String folderName) throws TrackNotFoundException {
        for (Folder folder : folderList) {
            if (folder.getFolderName().equals(folderName)) {
                this.currentFolder = folder;
                return;
            }
        }
        throw new TrackNotFoundException("Folder not found: " + folderName);
    }

    public List<Track> getCurrentFolderTracks() {
        return currentFolder != null ? currentFolder.getMusicFiles() : new ArrayList<>();
    }
}