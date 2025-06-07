package com.model;

import com.repository.basicservice.BasicDBServiceFactory;
import com.repository.basicservice.interfaces.BasicDBService;
import com.repository.basicservice.interfaces.Playlist;
import com.repository.basicservice.interfaces.Track;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CurrentMediaTest {
    private static BasicDBService service;
    private static CurrentMedia currentMedia;
    private static List<Track> trackList;

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static int randomInt() {
        return UUID.randomUUID().hashCode();
    }

    private static Track createTrack() {
        return service.createTrack(randomString(), randomInt(), randomString(), service.createAlbum(randomString(), service.createArtist(randomString(), randomString())));
    }

    @Nested
    class TrackListTests {

        @BeforeAll
        static void setUp() {
            service = BasicDBServiceFactory.createBasicDBService();
        }

        @BeforeEach
        void init() {
            currentMedia = new CurrentMedia();
            trackList = new ArrayList<>();
        }

        @AfterAll
        static void tearDown() {
            if (service != null) {
                service.close();
            } else {
                fail("Instance of BasicDBService corrupted! This should not happen! Call your local DB2 admin.");
            }
            trackList = null;
            currentMedia = null;
        }

        @Test
        @DisplayName("should return empty list when no tracks are added")
        void getNullWhenTrackListIsEmpty() {
            assertTrue(currentMedia.getTrackList().isEmpty(), "Track list should have been empty but returned " + currentMedia.getTrackList().size() + " elements.");
        }

        @Test
        @DisplayName("should return same number of added tracks")
        void addedOneTrack_ShouldReturnOneTrack() {
            trackList.add(createTrack());
            currentMedia.setTrackList(trackList);
            assertEquals(trackList.size(), currentMedia.getTrackList().size(), "Track list size should have returned " + trackList.size() + " but returned " + currentMedia.getTrackList().size());
        }

        @Test
        @DisplayName("should return same track when track is added once")
        void addedTrack_ShouldReturnSameTrack() {
            trackList.add(createTrack());
            currentMedia.setTrackList(trackList);
            assertEquals(trackList.getFirst(), currentMedia.getTrackList().getFirst(), "Track list should have returned " + trackList.getFirst() + " but returned " + currentMedia.getTrackList().getFirst());
        }

        @Test
        @DisplayName("should return multiple tracks when multiple tracks are added")
        void addedMultipleTracks_ShouldReturnMultipleTracks() {
            trackList.add(createTrack());
            trackList.add(createTrack());
            trackList.add(createTrack());
            currentMedia.setTrackList(trackList);
            assertEquals(trackList.size(), currentMedia.getTrackList().size(), "Track list size should have returned " + trackList.size() + " but returned " + currentMedia.getTrackList().size());
        }

        @Test
        @DisplayName("should handle empty track list without throwing")
        void setNullTrackList_ShouldReturnEmptyList() {
            currentMedia.setTrackList(List.of());
            assertNotNull(currentMedia.getTrackList(), "Track list should not be null after setting null.");
            assertTrue(currentMedia.getTrackList().isEmpty(), "Track list should be empty if null is set.");
        }

        @Test
        @DisplayName("modifying returned list should not affect internal list if defensive copy is used")
        void modifyingReturnedList_ShouldNotAffectInternalList() {
            Track track = createTrack();
            trackList.add(track);
            currentMedia.setTrackList(trackList);

            currentMedia.getTrackList().clear();
            assertEquals(1, currentMedia.getTrackList().size(), "Internal list should not have been cleared.");
        }
    }

    @Nested
    class CurrentTrackTests {

        @BeforeAll
        static void setUp() {
            service = BasicDBServiceFactory.createBasicDBService();
        }

        @BeforeEach
        void init() {
            currentMedia = new CurrentMedia();
        }

        @AfterAll
        static void tearDown() {
            if (service != null) {
                service.close();
            }
        }

        @Test
        @DisplayName("if not set up, should return null")
        void shouldReturnNullWhenNotSetUp() {
            assertNull(currentMedia.getCurrentTrack(), "Current track should have been null but returned " + currentMedia.getCurrentTrack());
        }

        @Test
        @DisplayName("should return same track when track is added once")
        void addedTrack_ShouldReturnSameTrack() {
            Track track = createTrack();
            currentMedia.setCurrentTrack(track);
            assertEquals(track, currentMedia.getCurrentTrack(), "Current track should have returned " + track + " but returned " + currentMedia.getCurrentTrack());
        }

        @Test
        @DisplayName("should accept null as current track")
        void setNullCurrentTrack_ShouldReturnNull() {
            currentMedia.setCurrentTrack(null);
            assertNull(currentMedia.getCurrentTrack(), "Current track should be null after setting null.");
        }

        @Test
        @DisplayName("should overwrite current track when setting new one")
        void overwriteCurrentTrack_ShouldReturnNewTrack() {
            Track firstTrack = createTrack();
            Track secondTrack = createTrack();
            currentMedia.setCurrentTrack(firstTrack);
            currentMedia.setCurrentTrack(secondTrack);
            assertEquals(secondTrack, currentMedia.getCurrentTrack(), "Current track should have been updated to the second one.");
        }
    }

    @Nested
    class CurrentPlaylistTests {
        @BeforeAll
        static void setUp() {
            service = BasicDBServiceFactory.createBasicDBService();
        }

        @BeforeEach
        void init() {
            currentMedia = new CurrentMedia();
        }

        @AfterAll
        static void tearDown() {
            if (service != null) {
                service.close();
            }
        }

        @Test
        @DisplayName("if not set up, should return null")
        void shouldReturnNullWhenNotSetUp() {
            assertNull(currentMedia.getCurrentPlaylist(), "Current playlist should have been null but returned " + currentMedia.getCurrentPlaylist());
        }

        @Test
        @DisplayName("should return same playlist when playlist is added once")
        void addedPlaylist_ShouldReturnSamePlaylist() {
            Playlist playlist = service.createPlaylist(randomString());
            currentMedia.setCurrentPlaylist(playlist);
            assertEquals(playlist, currentMedia.getCurrentPlaylist(), "Current playlist should have returned " + playlist + " but returned " + currentMedia.getCurrentPlaylist());
        }

        @Test
        @DisplayName("should accept null as current playlist")
        void setNullPlaylist_ShouldReturnNull() {
            currentMedia.setCurrentPlaylist(null);
            assertNull(currentMedia.getCurrentPlaylist(), "Current playlist should be null after setting null.");
        }

        @Test
        @DisplayName("should overwrite current playlist when setting new one")
        void overwriteCurrentPlaylist_ShouldReturnNewPlaylist() {
            Playlist first = service.createPlaylist(randomString());
            Playlist second = service.createPlaylist(randomString());
            currentMedia.setCurrentPlaylist(first);
            currentMedia.setCurrentPlaylist(second);
            assertEquals(second, currentMedia.getCurrentPlaylist(), "Current playlist should have been updated to the second one.");
        }
    }

    @Nested
    class CurrentMediaTests {
        @BeforeAll
        static void setUp() {
            service = BasicDBServiceFactory.createBasicDBService();
        }

        @BeforeEach
        void init() {
            currentMedia = new CurrentMedia();
        }

        @AfterAll
        static void tearDown() {
            if (service != null) {
                service.close();
            }
        }

        @Test
        @DisplayName("should be thread-safe when multiple threads access track list")
        void concurrentAccess_ShouldNotThrow() {
            currentMedia.setTrackList(List.of(createTrack(), createTrack()));
            assertDoesNotThrow(() -> {
                Runnable r = () -> currentMedia.getTrackList().forEach(Track::getName);
                Thread t1 = new Thread(r);
                Thread t2 = new Thread(r);
                t1.start();
                t2.start();
                t1.join();
                t2.join();
            });
        }

    }
}