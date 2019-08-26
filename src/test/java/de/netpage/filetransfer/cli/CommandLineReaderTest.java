package de.netpage.filetransfer.cli;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.*;

/**
 * Testcases for {@link CommandLineReader}.
 */
public class CommandLineReaderTest {

    @Test
    public void readNoArguments() throws Exception {
        final CommandLineArguments read = CommandLineReader.read(new String[]{});
        assertNotNull(read);
        assertTrue(read.valid());
        assertFalse(read.isHelp());
        assertEquals(8080, read.getPort());
        assertEquals("route.properties", read.getRoutes());
        assertNotNull(read.toString());
    }

    @Test
    public void readHelp() throws Exception {
        final CommandLineArguments read = CommandLineReader.read(new String[]{"-h"});
        assertNotNull(read);
        assertTrue(read.valid());
        assertTrue(read.isHelp());
        assertEquals("route.properties", read.getRoutes());
    }

    @Test
    public void readRoute() throws Exception {
        final CommandLineArguments read = CommandLineReader.read(new String[]{"-r", "newRoute.properties"});
        assertNotNull(read);
        assertTrue(read.valid());
        assertFalse(read.isHelp());
        assertEquals("newRoute.properties", read.getRoutes());
    }

    @Test
    public void readOtherParameter() throws Exception {
        final CommandLineArguments read = CommandLineReader.read(new String[]{"-invalid", "--invalid2", ""});
        assertNotNull(read);
        assertTrue(read.valid());
    }

    @Test
    public void printHelp() throws Exception {
        CommandLineReader.printHelp();
    }

    @Test
    public void testConstructor() throws Exception {
        final Constructor<CommandLineReader> c = CommandLineReader.class.getDeclaredConstructor();
        c.setAccessible(true);
        final CommandLineReader u = c.newInstance();
        assertNotNull(u);
    }
}