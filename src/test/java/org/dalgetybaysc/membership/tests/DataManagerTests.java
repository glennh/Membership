package org.dalgetybaysc.membership.tests;

import org.apache.commons.io.FileUtils;
import org.dalgetybaysc.membership.DataManager;
import org.dalgetybaysc.membership.Utils;
import org.dalgetybaysc.membership.classes.Membership;
import org.junit.Before;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DataManagerTests
{
    @Before
    public void setupDb() throws IOException {
        DataManager.setConfigFilePath("test.hibernate.cfg.xml");
        DataManager.truncateTable(Membership.class.getSimpleName());
    }

    @Test
    public void truncateTable() throws ParseException {
        assertEquals(0, (int) DataManager.getNumMemberships());
        int id = DataManager.addNewMembership(999, Membership.MembershipClass.FAMILY, Utils.getDate("12/04/2018"), "");
        assertEquals(1, (int) DataManager.getNumMemberships());
        DataManager.truncateTable(Membership.class.getSimpleName());
        assertEquals(0, (int) DataManager.getNumMemberships());
    }

    @Test
    public void addNewMembership() throws ParseException {
        assertEquals(0, (int) DataManager.getNumMemberships());
        assertEquals(1, (int) DataManager.getNextAvailableMembershipNumber());

        int id = DataManager.addNewMembership(999, Membership.MembershipClass.FAMILY, Utils.getDate("12/04/2018"), "");
        assertEquals(1, id);
        assertEquals(1, (int) DataManager.getNumMemberships());
        assertEquals(2, (int) DataManager.getNextAvailableMembershipNumber());
    }

    @Test
    public void getMembership() throws ParseException {
        int id = DataManager.addNewMembership(999, Membership.MembershipClass.FAMILY, Utils.getDate("12/04/2018"), "");
        Membership m = DataManager.getMembership(id);
        assertNotNull(m);
        assertEquals(999, m.getMainMemberId());
    }

    @Test
    public void getMembershipNonExisting() {
        boolean exeptionThrown = false;
        try {
            Membership m = DataManager.getMembership(99);
        }
        catch (Exception e) {
            exeptionThrown = true;
            assertEquals(e.getMessage(), "No record for membership id 99");
        }
        assertEquals(true, exeptionThrown);
    }

    @Test
    public void retireMembership() throws ParseException {
        int id = DataManager.addNewMembership(999, Membership.MembershipClass.FAMILY, Utils.getDate("12/04/2018"), "");
        Membership m = DataManager.getMembership(id);
        assertEquals(1, (int) DataManager.getNumMemberships());
        assertEquals(2, (int) DataManager.getNextAvailableMembershipNumber());
        assertEquals(Membership.MembershipStatus.CURRENT, m.getStatus());

        DataManager.retireMembership(id);
        m = DataManager.getMembership(id);
        assertEquals(0, (int) DataManager.getNumMemberships());
        assertEquals(2, (int) DataManager.getNextAvailableMembershipNumber());
        assertEquals(Membership.MembershipStatus.OLD, m.getStatus());
    }

}
