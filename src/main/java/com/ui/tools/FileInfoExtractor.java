package com.ui.tools;


import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class FileInfoExtractor {
    private static Tag getFileTag(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        AudioFile audioFile = AudioFileIO.read(file); // 'file' is your selected media file

        return audioFile.getTag();
    }

    public static String getTrackTitle(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
            return getFileTag(file).getFirst(FieldKey.TITLE);
    }

    public static String getAlbumTitle(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        return getFileTag(file).getFirst(FieldKey.ALBUM);
    }

    public static String getArtistTitle(File file) throws CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException, IOException {
        return getFileTag(file).getFirst(FieldKey.ALBUM);
    }
}
