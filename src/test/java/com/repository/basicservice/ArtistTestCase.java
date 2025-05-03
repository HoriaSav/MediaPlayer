package com.repository.basicservice;

import com.repository.basicservice.interfaces.Artist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

public class ArtistTestCase extends AbstractEntityTestCase {
    @ParameterizedTest(
            name = "createArtist with invalid name - #{index} - Arg={0}"
    )
    @NullAndEmptySource
    @ValueSource(
            strings = {" ", "\t", "\n"}
    )
    public void testCreateArtistInvalidName(String name) {
        String genre = UUID.randomUUID().toString();
        boolean failed = this.verifyWithInvalidParameters(name, genre);
        if (failed) {
            Assertions.fail("Creating a Artist did work although name was '" + name + "' and with genre being valid!");
        }

    }

    @ParameterizedTest(
            name = "createArtist with invalid genre - #{index} - Arg={0}"
    )
    @ValueSource(
            strings = {" ", "\t", "\n"}
    )
    public void testCreateArtistInvalidGenre(String genre) {
        String name = UUID.randomUUID().toString();
        boolean failed = this.verifyWithInvalidParameters(name, genre);
        if (failed) {
            Assertions.fail("Creating a Artist did work although genre was '" + genre + "' with name being valid!");
        }

    }

    private boolean verifyWithInvalidParameters(String name, String genre) {
        boolean failed = false;

        try {
            service.createArtist(name, genre);
            failed = true;
        } catch (AssertionError var5) {
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("caught Exception of type " + e.getClass().getName() + " instead of an AssertionError. See stacktrace for further information.");
        }

        return failed;
    }

    @Test
    public void testCreateArtistCorrect() {
        String name = UUID.randomUUID().toString();
        String genre = UUID.randomUUID().toString();
        Artist artist = null;

        try {
            artist = service.createArtist(name, genre);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Creating a Ward object failed although both parameters were valid! See stacktrace for further information.");
        }

        Assertions.assertNotNull(artist, "Expected a Ward object, createWard(...) returned NULL");
        Assertions.assertEquals(0L, artist.getObjectID(), "The ObjectID of a freshly created Ward object should be an INVALID_OBJECT_ID.");
        Assertions.assertEquals(name, artist.getName(), "The ward's name did not match the expected one");
        Assertions.assertEquals(genre, artist.getGenre(), "The ward's number of beds did not match the expected one");
        List<Artist> wards = service.getArtists("");
        Assertions.assertFalse(this.containsWard(wards, artist, false), "Searching for a created, but not stored ward should not return any hit");
    }

    private boolean containsWard(List<Artist> artists, Artist artist, boolean lookAtObjectIdToo) {
        Assertions.assertNotNull(artists, "Expected a List of Ward objects, got NULL");

        for(Artist listArtist : artists) {
            if (listArtist.getName().equals(artist.getName()) && listArtist.getGenre().equals(artist.getGenre())) {
                if (!lookAtObjectIdToo) {
                    return true;
                }

                if (listArtist.getObjectID() == artist.getObjectID()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Test
    public void testStoreWardInsertGetWard() {
        Artist artist = this.createArtist();
        this.testStoreWardGetWard(artist, true);
    }

    @Test
    public void testStoreWardUpdateGetWard() {
        Artist ward = this.createArtist();
        long storedWardID = 0L;

        try {
            storedWardID = service.store(ward);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Storing a Ward object failed! See stacktrace for further information.");
        }

        ward = service.getArtist(storedWardID);
        Assertions.assertNotNull(ward, "Getting a Ward object with a valid opjectID returned null!");
        ward.setName(UUID.randomUUID().toString());
        this.testStoreWardGetWard(ward, false);
        ward.setGenre(UUID.randomUUID().toString());
        this.testStoreWardGetWard(ward, false);
    }

    private Artist testStoreWardGetWard(Artist ward, boolean insert) {
        List<Artist> oldWards = service.getArtists("");
        Assertions.assertNotNull(oldWards, "Expected a List, getWards(...) returned NULL");
        int oldNumberOfWards = oldWards.size();
        long storedWardID = 0L;

        try {
            storedWardID = service.store(ward);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Storing a Ward object failed! See stacktrace for further information.");
        }

        Assertions.assertTrue(ward.isPersistent(), "Calling 'isPersistent()' after storing returned false");
        List<Artist> newWards = service.getArtists("");
        Assertions.assertNotNull(newWards, "Expected a List, getWards(...) returned NULL");
        int newNumberOfWards = newWards.size();
        if (insert) {
            Assertions.assertEquals(oldNumberOfWards + 1, newNumberOfWards, "Number of Ward objects in db did not increase by one after inserting.");
        } else {
            Assertions.assertEquals(oldNumberOfWards, newNumberOfWards, "Number of Ward objects in db changed although an update should have been done");
        }

        Artist getWard = null;

        try {
            getWard = service.getArtist(storedWardID);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Getting a Ward object with a valid objectID failed! See stacktrace for further information.");
        }

        Assertions.assertNotNull(getWard, "Getting a Ward object with a valid objectID returned null!");
        Assertions.assertEquals(ward.getName(), getWard.getName(), "The Ward's name was not correctly received");
        Assertions.assertEquals(ward.getGenre(), getWard.getGenre(), "The Ward's number of beds was not correctly received");
        Assertions.assertEquals(storedWardID, getWard.getObjectID(), "The Ward object's objectID was not the requested one.");
        return getWard;
    }

    @ParameterizedTest(
            name = "getWard with invalid OID values - #{index} - Arg={0}"
    )
    @ValueSource(
            longs = {Long.MIN_VALUE, -42L, 0L, 0L}
    )
    public void testGetWardNegativeInvalid(long input) {
        boolean failed = false;
        Artist artist = null;

        try {
            service.getArtist(input);
            failed = true;
        } catch (AssertionError var6) {
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Caught an Exception of type " + e.getClass().getName() + " instead of an AssertionError. See stacktrace for further information.");
        }

        if (failed) {
            Assertions.fail("Getting a Ward with an invalid objectID did work");
        }

        Assertions.assertNull(artist, "Retrieving a Ward object with objectID '" + input + "' did return an unexpected object. See JavaDoc for description!");
    }

    @ParameterizedTest(
            name = "getWard with invalid OID values - #{index} - Arg={0}"
    )
    @ValueSource(
            longs = {Long.MAX_VALUE}
    )
    public void testGetWardPositiveInvalid(long input) {
        Artist artist = null;

        try {
            artist = service.getArtist(input);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Getting a Ward object with a valid objectID failed! See stacktrace for further information.");
        }

        Assertions.assertNull(artist, "Retrieving a Ward object with objectID '" + input + "' did return an unexpected object. See JavaDoc for description!");
    }

    @ParameterizedTest(
            name = "setName with invalid lastname - #{index} - Arg={0}"
    )
    @NullAndEmptySource
    @ValueSource(
            strings = {" ", "\t", "\n"}
    )
    public void testSetNameInvalid(String input) {
        boolean failed = this.verifyWithInvalidParameter(this.createArtist(), input);
        if (failed) {
            Assertions.fail("Setting a Ward's name did work although it was null. See JavaDoc for description.");
        }

    }

    private boolean verifyWithInvalidParameter(Artist artist, String name) {
        boolean failed = false;

        try {
            artist.setName(name);
            failed = true;
        } catch (AssertionError var5) {
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Caught Exception of type " + e.getClass().getName() + " instead of an AssertionError. See stacktrace for further information.");
        }

        return failed;
    }

    @Test
    public void testGetSetName() {
        Artist ward = this.createArtist();
        String name = UUID.randomUUID().toString();
        ward.setName(name);
        Assertions.assertEquals(name, ward.getName(), "Returned name did not match valid set one");
    }

    @Test
    public void testSetNumberOfBedsInvalid() {
        boolean failed = false;
        Artist ward = this.createArtist();
        String genre = null;

        try {
            ward.setGenre(genre);
            failed = true;
        } catch (AssertionError var5) {
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("Caught Exception of type " + e.getClass().getName() + " instead of an AssertionError. See stacktrace for further information.");
        }

        if (failed) {
            Assertions.fail("Setting the Ward's number of beds did work although it was a negative number. See JavaDoc for description.");
        }

    }

    @Test
    public void testGetSetNumberOfBeds() {
        Artist artist = this.createArtist();
        String genre = UUID.randomUUID().toString();
        artist.setGenre(genre);
        Assertions.assertEquals(genre, artist.getGenre(), "Returned number of beds did not match valid set one");
    }

//
//    @Test
//    public void testGetFreeBeds() {
//        int freeBeds = service.getFreeBeds((Ward)null);
//        Ward w1 = this.testStoreWardGetWard(this.createWard(), true);
//        Ward w2 = this.testStoreWardGetWard(this.createWard(), true);
//        int freeBedsAfterWardsAreAdded = service.getFreeBeds((Ward)null);
//        int expectedFreeBeds = freeBeds + w1.getNumberOfBeds() + w2.getNumberOfBeds();
//        Assertions.assertEquals(expectedFreeBeds, freeBedsAfterWardsAreAdded, "The amount of free beds did not increase after adding new wards to the hospital.");
//        int occupiedBeds = 2;
//        this.createHospitalStayPersistent(w1, false);
//        this.createHospitalStayPersistent(w2, false);
//        this.createHospitalStayPersistent(w1, true);
//        int freeBedsAfterAdmission = service.getFreeBeds((Ward)null);
//        Assertions.assertEquals(expectedFreeBeds - occupiedBeds, freeBedsAfterAdmission, "The amount of free beds did not decrease for the given ward after patient admission!");
//    }
//
//    @Test
//    public void testGetAllocatedBedsByWard() {
//        int numberOfBeds = (int)(Math.random() + (double)1.0F) * 100;
//        Ward ward = this.createWard();
//        ward.setNumberOfBeds(numberOfBeds);
//        ward = this.testStoreWardGetWard(ward, true);
//        int allocatedBeds = service.getAllocatedBeds(ward);
//        Assertions.assertEquals(0, allocatedBeds, "The amount of allocated beds does not match the expected amount of allocated beds for the given ward.");
//        int occupiedBeds = 2;
//        this.createHospitalStayPersistent(ward, false);
//        this.createHospitalStayPersistent(ward, false);
//        this.createHospitalStayPersistent(ward, true);
//        int allocatedBedsAfterAdmission = service.getAllocatedBeds(ward);
//        Assertions.assertEquals(occupiedBeds, allocatedBedsAfterAdmission, "The amount of allocated beds does not match the expected amount of allocated beds for the given ward.");
//    }
//
//    @Test
//    public void testGetAllocatedBedsByWardInvalid() {
//        try {
//            service.getAllocatedBeds(this.createWard());
//        } catch (AssertionError var2) {
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Caught Exception of type " + e.getClass().getName() + " instead of an AssertionError. See stacktrace for further information.");
//        }
//
//    }
//
//    @Test
//    public void testGetAllocatedBeds() {
//        int allocatedBeds = service.getAllocatedBeds((Ward)null);
//        Ward w1 = this.testStoreWardGetWard(this.createWard(), true);
//        Ward w2 = this.testStoreWardGetWard(this.createWard(), true);
//        int occupiedBeds = 2;
//        this.createHospitalStayPersistent(w1, false);
//        this.createHospitalStayPersistent(w2, false);
//        this.createHospitalStayPersistent(w1, true);
//        int allocatedBedsAfterAdmission = service.getAllocatedBeds((Ward)null);
//        Assertions.assertEquals(allocatedBeds + occupiedBeds, allocatedBedsAfterAdmission, "The amount of allocated beds did not increase for the given ward after patient admission!");
//    }
//
//    @Test
//    public void testGetWards() {
//        List<Ward> previous = service.getWards();
//        String msgResultNull = "Result list of getWards() call returned NULL!";
//        Assertions.assertNotNull(previous, "Result list of getWards() call returned NULL!");
//        Ward ward1 = this.createWardPersistent(this.createWard());
//        Ward ward2 = this.createWardPersistent(this.createWard());
//        Ward ward3 = this.createWardPersistent(this.createWard());
//        List<Ward> after = service.getWards();
//        Assertions.assertNotNull(after, "Result list of getWards() call returned NULL!");
//        Assertions.assertNotEquals(0, after.size(), "Result list of getWards() call had a size of 0.");
//        Assertions.assertEquals(previous.size() + 3, after.size(), "Number of Ward objects did not increase by three in DB.");
//        boolean foundW1 = false;
//        boolean foundW2 = false;
//        boolean foundW3 = false;
//
//        for(Ward w : after) {
//            if (w.getObjectID() == ward1.getObjectID()) {
//                foundW1 = true;
//            }
//
//            if (w.getObjectID() == ward2.getObjectID()) {
//                foundW2 = true;
//            }
//
//            if (w.getObjectID() == ward3.getObjectID()) {
//                foundW3 = true;
//            }
//        }
//
//        String msg = "Result list by getWards() call did not contain an expected Ward object inserted before: ";
//        Assertions.assertTrue(foundW1, "Result list by getWards() call did not contain an expected Ward object inserted before: " + ward1.getObjectID());
//        Assertions.assertTrue(foundW2, "Result list by getWards() call did not contain an expected Ward object inserted before: " + ward2.getObjectID());
//        Assertions.assertTrue(foundW3, "Result list by getWards() call did not contain an expected Ward object inserted before: " + ward3.getObjectID());
//    }
}
