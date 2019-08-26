package de.netpage.filetransfer.cli;

import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CommandLine;
import io.vertx.core.cli.annotations.CLIConfigurator;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Reads the arguments from the command line. The arguments are transformed into an object
 * of type {@link CommandLineArguments}.
 */
public class CommandLineReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineReader.class);

    private CommandLineReader() {
        // Only static methods.
    }

    /**
     * Read the arguments from command line.
     *
     * @param args Arguments from command line
     * @return Instance of Arguments
     */
    public static CommandLineArguments read(final String[] args) {
        final List<String> argList = Arrays.asList(args);
        LOGGER.debug("Reading arguments: {}", Arrays.toString(args));

        final CLI cli = CLI.create(CommandLineArguments.class);
        final CommandLine commandLine = cli.parse(argList);
        final CommandLineArguments instance = new CommandLineArguments();
        CLIConfigurator.inject(commandLine, instance);

        LOGGER.debug("Created arguments: {}", instance);
        return instance;
    }

    /**
     * Print the usage.
     */
    public static void printHelp() {
        final CLI cli = CLI.create(CommandLineArguments.class);
        final StringBuilder sb = new StringBuilder();
        cli.usage(sb);
        System.out.println(sb.toString());
    }

}
