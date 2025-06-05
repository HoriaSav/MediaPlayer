package com.service;

import com.exception.FolderOperationException;
import com.model.CurrentMedia;
import com.model.Folder;
import com.repository.basicservice.BasicDBServiceFactory;
import com.repository.basicservice.interfaces.*;
import com.util.FileInfoExtractor;
import com.util.InputValidator;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * @author horia
 * <p>
 * handles local music library
 */
public class MusicLibraryServiceImpl implements MusicLibraryService {
    private final BasicDBService service;
    private final CurrentMedia currentMedia;
    private final PlayerService playerService;

    public MusicLibraryServiceImpl() {
        this.service = BasicDBServiceFactory.createBasicDBService();
        this.currentMedia = new CurrentMedia();
        this.playerService = new PlayerServiceImpl(currentMedia);
    }

    @Override
    public void playTrack(String trackName) {
        playerService.play(trackName);
    }

    @Override
    public void playPause() {
        playerService.playPauseTrack();
    }

    @Override
    public void skip() {
        playerService.playNextTrack();
    }

    @Override
    public void previous() {
        playerService.playPreviousTrack();
    }

    @Override
    public void mute() {
        playerService.mute();
    }

    @Override
    public List<Track> getTracks() {
        return currentMedia.getTrackList();
    }

    @Override
    public Track getCurrentTrack() {
        return currentMedia.getCurrentTrack();
    }

    @Override
    public List<Playlist> getPlaylists() {
        return service.getPlaylists();
    }

    @Override
    public void addPlaylist() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Music Folder");
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            try {
                InputValidator.validateFolder(selectedDirectory);
                Folder folder = new Folder(selectedDirectory);
                if(service.getPlaylist(folder.getFolderName()) == null) {
                    Playlist playlist = service.createPlaylist(folder.getFolderName());
                    service.store(playlist);
                    currentMedia.setCurrentPlaylist(service.getPlaylist(folder.getFolderName()));
                    addTracksFromLocalToDB(folder, playlist);
                    List<Track> trackList = service.getPlaylistTracks(currentMedia.getCurrentPlaylist());
                    currentMedia.setTrackList(trackList);
                }
                else {
                    throw new FolderOperationException("Playlist already exists");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new FolderOperationException("No directory selected");
        }
    }

    private void addTracksFromLocalToDB(Folder folder, Playlist playlist) {
        File[] files = folder.getFolderFile().listFiles();
        if (files == null) {
            throw new FolderOperationException("Unable to list files in folder: " + folder.getFolderFile().getPath());
        }
        int trackNumber = 1;
        for (File file : files) {
            if (InputValidator.isMusicFile(file)) {
                System.out.println(file.getName());
                storeTrack(file, trackNumber++, playlist);
            }
        }
    }

    private void storeTrack(File file, int trackNumber, Playlist playlist) {
        try {
            String name = FileInfoExtractor.getTrackTitle(file);
            int duration = FileInfoExtractor.getTrackDuration(file);
            String path = file.getPath();
            Artist artist = service.getArtist(FileInfoExtractor.getArtistName(file));
            if (artist == null) {
                artist = service.createArtist(FileInfoExtractor.getArtistName(file), "unknown");
            }
            Album album = service.getAlbum(FileInfoExtractor.getAlbumTitle(file), artist);
            if (album == null) {
                album = service.createAlbum(FileInfoExtractor.getAlbumTitle(file), artist);
            }
            Track track = service.getTrack(name, artist.getName());
            if (track == null) {
                track = service.createTrack(name, duration, path, album);
                service.store(track);
            }
            service.store(service.createPlaylistTrack(playlist, track, trackNumber));
        } catch (Exception e) {
            //TODO: implement logging
        }
    }

    @Override
    public Playlist getCurrentPlaylist() {
        return currentMedia.getCurrentPlaylist();
    }

    @Override
    public void setCurrentPlaylist(String playlist) {
        currentMedia.setCurrentPlaylist(service.getPlaylist(playlist));
        currentMedia.setTrackList(service.getPlaylistTracks(currentMedia.getCurrentPlaylist()));
    }

    @Override
    public void close() {
        if (service != null) {
            service.close();
        }
    }
}
