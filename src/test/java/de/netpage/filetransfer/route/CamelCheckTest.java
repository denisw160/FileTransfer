package de.netpage.filetransfer.route;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Testcases for {@link CamelCheck}.
 */
public class CamelCheckTest {

    private CamelCheck check;

    @Before
    public void setUp() throws Exception {
        check = new CamelCheck();
    }

    @Test
    public void getRoutes() throws Exception {
        final List<RouteCheck> routes = check.getRoutes();
        assertNotNull(routes);
        assertEquals(0, routes.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getRoutesUnmodifiable() throws Exception {
        check.getRoutes().add(new RouteCheck());
        fail();
    }

    @Test
    public void addRoute() throws Exception {
        final List<RouteCheck> routes = check.getRoutes();
        assertEquals(0, routes.size());

        check.addRoute(new RouteCheck());
        assertEquals(1, routes.size());
    }

    @Test
    public void removeRoute() throws Exception {
        addRoute();

        final List<RouteCheck> routes = check.getRoutes();
        final RouteCheck routeCheck = routes.get(0);
        check.removeRoute(routeCheck);

        assertEquals(0, routes.size());
    }

    @Test
    public void testToString() throws Exception {
        final String s = check.toString();
        assertNotNull(s);
    }

}