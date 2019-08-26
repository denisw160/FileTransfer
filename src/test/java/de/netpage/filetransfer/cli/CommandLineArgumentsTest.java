package de.netpage.filetransfer.cli;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Testcases for {@link CommandLineArguments}.
 */
public class CommandLineArgumentsTest {

    @Test
    public void dummy() throws Exception {
        final CommandLineArguments args = new CommandLineArguments();
        args.setHelp(true);
        args.setRoutes("sample");
        assertNotNull(args);
    }

}