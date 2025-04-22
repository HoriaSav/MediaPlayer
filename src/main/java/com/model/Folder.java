package com.model;

import com.util.FileInfoExtractor;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private final File folder;
    private final List<Track> musicFiles;
    private final String name;

    public Folder(File folder) {
        this.folder = folder;
        this.name = folder.getName();
        this.musicFiles = new ArrayList<>();
        setFolder();
    }

    //TODO: extract method in FolderService
    private void setFolder() {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (isMusicFile(file)) {
                    try {
                        String name = FileInfoExtractor.getTrackTitle(file);
                        String artist = FileInfoExtractor.getArtistName(file);
                        String album = FileInfoExtractor.getAlbumTitle(file);
                        int duration = FileInfoExtractor.getTrackDuration(file);
                        String path = file.getPath();
                        Track track = new Track(name, artist, album, duration, path);
                        musicFiles.add(track);
                    }
                    catch (CannotReadException | TagException | InvalidAudioFrameException | ReadOnlyFileException |
                           IOException e){
                        //TODO: exception handling
                    }
                }
            }
        }
    }

    //TODO: extract method in FolderService
    private boolean isMusicFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".mp3") || name.endsWith(".wav")
                || name.endsWith(".m4a") || name.endsWith(".aac");
    }

    public List<Track> getMusicFiles() {
        return musicFiles;
    }

    public String getFolderName() {
        return name;
    }
}
