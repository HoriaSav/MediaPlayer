package com.model;

import com.exception.AudioFileProcessingException;
import com.exception.FolderOperationException;
import com.exception.ValidationException;
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
    private final File folderFile;
    private final List<Track> musicFiles;
    private final String name;

    public Folder(File folderFile) {
        this.folderFile = folderFile;
        this.name = folderFile.getName();
        this.musicFiles = new ArrayList<>();
    }

    public void addMusicFile(Track track) throws ValidationException {
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