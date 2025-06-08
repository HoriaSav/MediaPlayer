package com.repository.basicservice;

import com.repository.basicservice.interfaces.Artist;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.UUID;

public abstract class AbstractEntityTestCase extends AbstractServiceTestCase {
    protected final Artist createArtist() {
        String name = UUID.randomUUID().toString();
        String genre = UUID.randomUUID().toString();
        Artist artist = null;

        try {
            artist = service.createArtist(name, genre);
            Assertions.assertNotNull(artist, "Expected a Ward object, createWard(...) returned NULL");
            Assertions.assertEquals(0L, artist.getObjectID(), "The objectID of a newly created Ward object should be an INVALID_OBJECT_ID.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assertions.fail("Creating a Ward object failed although name and number of beds were valid values! See stacktrace for further information.");
        }

        return artist;
    }

    protected final Artist createArtistPersistent(Artist artist) {
        List<Artist> wards = service.getArtistsByGenre("");
        Assertions.assertNotNull(wards, "Expected a List, getWards returned NULL");
        int prevCount = wards.size();
        long storedArtistID = 0L;

        try {
            storedArtistID = service.store(artist);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assertions.fail("Storing a Ward object failed! See stacktrace for further information.");
        }

        Assertions.assertTrue(artist.isPersistent(), "Calling 'isPersistent()' after storing returned false");
        List<Artist> newArtists = service.getArtistsByGenre("");
        Assertions.assertNotNull(newArtists, "Expected a List, getArtists returned NULL");
        int newCount = newArtists.size();
        Assertions.assertEquals(prevCount + 1, newCount, "Number of Artists objects in db did not increase by one after inserting.");
        Artist getArtist = null;

        try {
            getArtist = service.getArtist(storedArtistID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assertions.fail("Getting a Artist object with a valid objectID failed! See stacktrace for further information.");
        }

        Assertions.assertNotNull(getArtist, "Getting a Artist object with a valid objectID returned NULL");
        Assertions.assertEquals(artist.getName(), getArtist.getName(), "The Artist's name was not correctly received");
        Assertions.assertEquals(artist.getGenre(), getArtist.getGenre(), "The Artist's genre was not correctly received");
        Assertions.assertEquals(storedArtistID, getArtist.getObjectID(), "The Artist object's objectID was not the requested one.");
        return getArtist;
    }
}
