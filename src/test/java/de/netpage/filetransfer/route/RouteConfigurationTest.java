package de.netpage.filetransfer.route;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Testcases for {@link RouteConfiguration}.
 */
public class RouteConfigurationTest {

    @Test
    public void testToString() throws Exception {
        final RouteConfiguration conf = new RouteConfiguration();
        assertNotNull(conf.toString());
    }

}