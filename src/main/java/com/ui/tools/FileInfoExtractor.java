package com.ui.tools;


import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;

public class FileInfoExtractor {
    public static String getTrackTitle(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file); // 'file' is your selected media file
            Tag tag = audioFile.getTag();

            return tag.getFirst(FieldKey.TITLE);
        } catch (Exception e) {
            e.printStackTrace();
            return file.getName();
        }
    }

    public static String getAlbumTitle(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file); // 'file' is your selected media file
            Tag tag = audioFile.getTag();

            return tag.getFirst(FieldKey.ALBUM);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getArtistTitle(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file); // 'file' is your selected media file
            Tag tag = audioFile.getTag();

            return tag.getFirst(FieldKey.ARTIST);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
