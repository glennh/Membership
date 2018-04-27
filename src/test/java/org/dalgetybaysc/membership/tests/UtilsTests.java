package org.dalgetybaysc.membership.tests;

import org.dalgetybaysc.membership.Utils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UtilsTests {
    @Test
    public void getDate() throws ParseException {
        Date d = Utils.getDate("1/1/1980");
        assertEquals(d.getClass(), Date.class);
    }
}
