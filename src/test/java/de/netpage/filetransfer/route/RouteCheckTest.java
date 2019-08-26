package de.netpage.filetransfer.route;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Testcases for {@link RouteCheck}.
 */
public class RouteCheckTest {

    @Test
    public void testGet() throws Exception {
        final RouteCheck chk = new RouteCheck();
        assertNull(chk.getFrom());
        assertNull(chk.getTo());
        assertNull(chk.getExchangesCompleted());
        assertNull(chk.getExchangesFailed());
        assertNull(chk.getExchangesInflight());
        assertNull(chk.getExchangesTotal());
        assertNull(chk.getLastExchangeCompletedTimestamp());
        assertNull(chk.getLastExchangeFailureTimestamp());
    }

    @Test
    public void testToString() throws Exception {
        final RouteCheck chk = new RouteCheck();
        assertNotNull(chk.toString());
    }

}