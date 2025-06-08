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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            Assertions.fail("Getting a Artist object with a valid objectID failed! See stacktrace for further information.");
        }

        Assertions.assertNotNull(getArtist, "Getting a Artist object with a valid objectID returned NULL");
        Assertions.assertEquals(artist.getName(), getArtist.getName(), "The Artist's name was not correctly received");
        Assertions.assertEquals(artist.getGenre(), getArtist.getGenre(), "The Artist's genre was not correctly received");
        Assertions.assertEquals(storedArtistID, getArtist.getObjectID(), "The Artist object's objectID was not the requested one.");
        return getArtist;
    }
//
//    protected final Patient createPatient() {
//        return this.createPatient(UUID.randomUUID().toString(), UUID.randomUUID().toString());
//    }
//
//    protected final Patient createPatient(String lastname, String firstname) {
//        Patient p = null;
//
//        try {
//            p = service.createArtist(lastname, firstname);
//            Assertions.assertNotNull(p, "Expected a Patient object, createPatient(...) returned NULL");
//            Assertions.assertEquals(0L, p.getObjectID(), "The ObjectID of a newly created Patient object should be PersistentObject.INVALID_OBJECT_ID");
//            return p;
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assertions.fail("Creating a Patient object failed although 2 not-empty Strings are given! See stacktrace for further information.");
//            return p;
//        }
//    }
//
//    protected final void createHospitalStayPersistent(Ward ward, Date admissionDate, Date dischargeDate) {
//        Assertions.assertNotNull(ward);
//        Patient patient = this.createPatient();
//        Assertions.assertNotNull(patient, "could not create a patient");
//        HospitalStay h = service.createHospitalStay(patient, ward, admissionDate);
//        Assertions.assertNotNull(h, "Expected a HospitalStay object, createHospitalStay(...) returned NULL");
//        if (dischargeDate != null) {
//            h.setDischargeDate(dischargeDate);
//        }
//
//        long objectID = service.store(h);
//        Assertions.assertNotEquals(0L, objectID, "the returned objectID is incorrect after a successful store operation.");
//        Assertions.assertTrue(h.isPersistent(), "the hospital stay object is not in a persisted state after store operation.");
//        Patient p = h.getPatient();
//        Assertions.assertNotNull(p, "the associated patient is null after storing a new hospital stay!");
//        Assertions.assertTrue(p.isPersistent(), "the associated patient is not persisted after storing a new hospital stay!");
//        Assertions.assertNotNull(h.getWard(), "the associated ward is null after storing a new hospital stay!");
//        Assertions.assertTrue(h.getWard().isPersistent(), "the associated ward is not persisted after storing a new hospital stay!");
//        List<HospitalStay> hospitalStays = service.getHospitalStays(p.getObjectID());
//        Assertions.assertNotNull(hospitalStays, "Expected a populated List, however getHospitalStays(..) returned NULL");
//        Assertions.assertFalse(hospitalStays.size() < 1, "there is no hospital stay for this patient");
//        Assertions.assertFalse(hospitalStays.size() > 1, "there are too many hospital stays for this patient, only one should be returned");
//        HospitalStay lHospitalStay = (HospitalStay)hospitalStays.get(0);
//        Assertions.assertNotNull(lHospitalStay);
//        Assertions.assertEquals(h.getObjectID(), lHospitalStay.getObjectID(), "object id must match the expected value");
//        Assertions.assertEquals(0, DateHelper.compareDates(h.getAdmissionDate(), lHospitalStay.getAdmissionDate()), "admission date must agree with the default value.");
//        p = h.getPatient();
//        Assertions.assertNotNull(p, "patient should not be null");
//        Assertions.assertEquals(p.getObjectID(), lHospitalStay.getPatient().getObjectID(), "patient must agree with the default value.");
//        Assertions.assertNotNull(h.getWard(), "ward should not be null");
//        Assertions.assertEquals(h.getWard().getObjectID(), lHospitalStay.getWard().getObjectID(), "ward must agree with the default value.");
//        Assertions.assertEquals(0, DateHelper.compareDates(h.getAdmissionDate(), lHospitalStay.getAdmissionDate()), "admission date must agree with the default value.");
//        if (dischargeDate != null) {
//            Assertions.assertEquals(0, DateHelper.compareDates(h.getDischargeDate(), lHospitalStay.getDischargeDate()), "discharge date must agree with the default value.");
//        } else {
//            Assertions.assertNull(h.getDischargeDate());
//        }
//
//    }
//
//    protected final void createHospitalStayPersistent(Ward ward, boolean withDischargeDate) {
//        Date dischargeDate = null;
//        if (withDischargeDate) {
//            dischargeDate = DateHelper.getDate(20, 2, 2008);
//        }
//
//        this.createHospitalStayPersistent(ward, DateHelper.getDate(10, 2, 2008), dischargeDate);
//    }
}
