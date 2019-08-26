package de.netpage.filetransfer;

import de.netpage.filetransfer.cli.CommandLineArguments;
import de.netpage.filetransfer.cli.CommandLineReader;
import de.netpage.filetransfer.health.HealthVerticle;
import de.netpage.filetransfer.route.CamelVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;

/**
 * This is the main class for starting the service.
 */
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private Application() {
        // only static methods
    }

    /**
     * Start the service. The service reads the parameter from command line and
     * setup the system. For usage add --help as program argument.
     *
     * @param args Parameter for setup the environment.
     */
    public static void main(final String[] args) {
        LOGGER.debug("Starting Application");

        // Reading arguments from command line
        final CommandLineArguments arguments = CommandLineReader.read(args);
        // Show help, if arguments are invalid or help
        if (arguments.isHelp() || !arguments.valid()) {
            CommandLineReader.printHelp();
            System.exit(1);
        }

        // Start service
        final Vertx vertx = Vertx.vertx(new VertxOptions().setMetricsOptions(
                new DropwizardMetricsOptions()
                        .setEnabled(true)
                        .setJmxEnabled(true)
                        .setJmxDomain("vertx")
        ));
        vertx.deployVerticle(new CamelVerticle(arguments));
        vertx.deployVerticle(new HealthVerticle(arguments));

        LOGGER.debug("Application started");
    }

}
