package de.netpage.filetransfer.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Testcases for {@link DateUtils}.
 */
public class DateUtilsTest {

    @Test
    public void convertTimestamp() throws Exception {
        final String timestamp = DateUtils.convertTimestamp(new Date());
        assertNotNull(timestamp);

        assertNull(DateUtils.convertTimestamp(null));
    }

    @Test
    public void testConstructor() throws Exception {
        final Constructor<DateUtils> c = DateUtils.class.getDeclaredConstructor();
        c.setAccessible(true);
        final DateUtils u = c.newInstance();
        assertNotNull(u);
    }

}