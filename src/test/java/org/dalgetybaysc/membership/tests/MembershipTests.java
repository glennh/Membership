package org.dalgetybaysc.membership.tests;

import org.dalgetybaysc.membership.Utils;
import org.dalgetybaysc.membership.classes.Membership;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class MembershipTests {
    @Test
    public void createInstance() throws ParseException {
        Membership m = new Membership(1, 2, Membership.MembershipClass.FAMILY, Utils.getDate("01/03/2018"), null);
        assertEquals(1, m.getId());
        assertEquals(2, m.getMainMemberId());
        assertEquals(Membership.MembershipClass.FAMILY, m.getMemClass());
        assertEquals(Utils.getDate("01/03/2018"), m.getJoinedDate());
        assertEquals(null, m.getLeftDate());
        assertEquals(null, m.getComment());
        assertEquals(Membership.MembershipStatus.CURRENT, m.getStatus());
    }
}
