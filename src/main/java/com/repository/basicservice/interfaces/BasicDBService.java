package com.repository.basicservice.interfaces;

import com.repository.PDBGVersion;
import com.repository.PersistentObject;

import java.util.List;

/**
 * The BasicDBService interface provides methods to manage and interact with data
 * related to artists, albums, playlists, tracks, and associated entities.
 * It supports creating entities, retrieving entities by various parameters,
 * and performing operations on persistent objects in the database.
 */
@PDBGVersion
public interface BasicDBService {
    /**
     * Creates a new artist with the specified name and genre.
     *
     * @param name the name of the artist; must be a non-null and non-empty string
     * @param genre the genre of the artist; must be a non-null and valid string
     * @return the created Artist instance
     * @throws IllegalArgumentException if the name or genre is null or invalid
     */
    Artist createArtist(String name, String genre);

    /**
     * Creates a new album with the specified name and associated artist.
     *
     * @param name the name of the album; must be a non-null and non-empty string
     * @param artist the artist associated with the album; must be a non-null instance of Artist
     * @return the created Album instance
     * @throws IllegalArgumentException if the name is null, empty, or if the artist is null
     */
    Album createAlbum(String name, Artist artist);

    /**
     * Creates a new playlist with the specified name.
     *
     * @param name the name of the playlist; must be a non-null and non-empty string
     * @return the created Playlist instance
     * @throws IllegalArgumentException if the name is null or empty
     */
    Playlist createPlaylist(String name);

    /**
     * Creates a new track with the specified name, duration, path, and associated album.
     *
     * @param name the name of the track; must be a non-null and non-empty string
     * @param durationSec the duration of the track in seconds; must be a non-negative value
     * @param path the file path of the track; must be a non-null and valid string
     * @param album the album associated with the track; must be a non-null instance of Album
     * @return the created Track instance
     * @throws IllegalArgumentException if the name is null, empty, or invalid; if the duration is negative;
     *         if the path is null or invalid; or if the album is null
     */
    Track createTrack(String name, int durationSec, String path, Album album);

    /**
     * Creates a new PlaylistTrack instance that associates the specified track with a playlist
     * and assigns a specific order or position to the track within the playlist.
     *
     * @param playlist the playlist to which the track will be added; must be a non-null instance of Playlist
     * @param track the track to be added to the playlist; must be a non-null instance of Track
     * @param trackPlaylistNumber the position of the track within the playlist, as a zero-based index; must be non-negative
     * @return the created PlaylistTrack instance that represents the association of the track with the playlist
     * @throws IllegalArgumentException if the playlist or track is null, or if the trackPlaylistNumber is negative
     */
    PlaylistTrack createPlaylistTrack(Playlist playlist, Track track, int trackPlaylistNumber);

    /**
     * Retrieves a list of artists that belong to the specified genre.
     *
     * @param genre the genre of the artists to retrieve; must be a non-null and valid string
     * @return a list of artists that match the specified genre; may return an empty list if no artists are found
     */
    List<Artist> getArtists(String genre);

    /**
     * Retrieves the artist with the specified ID.
     *
     * @param id the unique identifier of the artist to retrieve
     * @return the artist associated with the specified ID, or null if no artist is found
     */
    Artist getArtist(long id);

    /**
     * Retrieves the artist associated with the specified name.
     *
     * @param name the name of the artist to retrieve; must be a non-null and non-empty string
     * @return the Artist instance associated with the specified name, or null if no artist is found
     */
    Artist getArtist(String name);

    /**
     * Retrieves a list of albums associated with the specified artist name.
     *
     * @param artistName the name of the artist whose albums are to be retrieved; must be a non-null, non-empty string
     * @return a list of albums associated with the specified artist name; may return an empty list if no albums are found
     * @throws IllegalArgumentException if the artistName is null or empty
     */
    List<Album> getAlbums(String artistName);

    /**
     * Retrieves the album with the specified ID.
     *
     * @param id the unique identifier of the album to retrieve
     * @return the album associated with the specified ID, or null if no album is found
     */
    Album getAlbum(long id);

    /**
     * Retrieves an album with the specified name and associated artist.
     *
     * @param name the name of the album to retrieve; must be a non-null and non-empty string
     * @param artist the artist associated with the album; must be a non-null instance of Artist
     * @return the Album instance that matches the specified name and artist, or null if no album is found
     * @throws IllegalArgumentException if the name is null, empty, or if the artist is null
     */
    Album getAlbum(String name, Artist artist);

    /**
     * Retrieves a list of all playlists available in the repository.
     *
     * @return a list of Playlist instances; the list may be empty if no playlists exist, but never null
     */
    List<Playlist> getPlaylists();

    /**
     * Retrieves a track based on the specified names.
     *
     * @param name the primary name of the track to retrieve; must be a non-null and non-empty string
     * @param name1 an additional name or identifier for the track; must be a non-null and non-empty string
     * @return the Track instance that matches the specified names, or null if no track is found
     * @throws IllegalArgumentException if name or name1 is null or empty
     */
    Track getTrack(String name, String name1);

    /**
     * Retrieves the list of tracks associated with the specified playlist.
     *
     * @param playlist the playlist from which to retrieve tracks; must be a non-null instance of Playlist
     * @return a list of Track instances associated with the specified playlist;
     *         the list may be empty if the playlist contains no tracks
     * @throws IllegalArgumentException if the playlist is null
     */
    List<Track> getPlaylistTracks(Playlist playlist);

    /**
     * Retrieves a list of tracks associated with the specified album.
     *
     * @param album the album from which to retrieve tracks; must be a non-null instance of Album
     * @return a list of Track instances associated with the specified album;
     *         the list may be empty if the album contains no tracks but will never be null
     * @throws IllegalArgumentException if the album is null
     */
    List<Track> getAlbumTracks(Album album);

    /**
     * Retrieves a list of tracks associated with the specified artist.
     *
     * @param artist the artist whose tracks are to be retrieved; must be a non-null instance of Artist
     * @return a list of Track instances associated with the specified artist;
     *         the list may be empty if the artist has no tracks but will never be null
     * @throws IllegalArgumentException if the artist is null
     */
    List<Track> getArtistTracks(Artist artist);

    /**
     * Retrieves a list of tracks marked as liked by the user.
     *
     * @return a list of Track instances representing the liked tracks;
     *         the list may be empty if no liked tracks exist but will never be null
     */
    List<Track> getLikedTracks();

    /**
     * Retrieves the playlist associated with the specified name.
     *
     * @param name the name of the playlist to retrieve; must be a non-null and non-empty string
     * @return the Playlist instance associated with the specified name, or null if no playlist is found
     * @throws IllegalArgumentException if the name is null or empty
     */
    Playlist getPlaylist(String name);

    /**
     * Stores the given persistent object in the database and returns its unique identifier.
     *
     * @param var1 the persistent object to store; must be a non-null instance of a class implementing PersistentObject
     * @return the unique identifier assigned to the stored object, or {@code PersistentObject.INVALID_OBJECT_ID} if the storage fails
     * @throws IllegalArgumentException if the provided object is null or invalid
     */
    long store(PersistentObject var1);

    /**
     * <p>
     * Closes the underlying connection or releases resources associated with the database service.
     * This method should be called when the database service is no longer needed to ensure proper
     * cleanup of resources and avoid potential memory leaks or resource contention issues.
     * </p>
     * <p>
     * It is recommended to call this method in a `finally` block or using a try-with-resources
     * construct where applicable. Calling this method multiple times should be safe, but no
     * further operations should be performed on the service after it has been closed.
     * </p>
     */
    void close();
}
