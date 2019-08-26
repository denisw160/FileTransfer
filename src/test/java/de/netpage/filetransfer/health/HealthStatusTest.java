package de.netpage.filetransfer.health;

import org.junit.Test;

import static de.netpage.filetransfer.health.HealthStatus.*;
import static org.junit.Assert.assertEquals;

/**
 * Testcases for {@link HealthStatus}.
 */
public class HealthStatusTest {

    @Test
    public void with() throws Exception {
        assertEquals(DEAD, ALIVE.with(DEAD));
        assertEquals(DEAD, DEAD.with(ALIVE));
        assertEquals(ERROR, ALIVE.with(ERROR));
        assertEquals(ERROR, ERROR.with(ALIVE));
        assertEquals(ERROR, ERROR.with(DEAD));
        assertEquals(ERROR, DEAD.with(ERROR));
    }

    @Test
    public void mark() throws Exception {
        assertEquals('*', ALIVE.mark());
        assertEquals('!', DEAD.mark());
        assertEquals('?', ERROR.mark());
    }

}