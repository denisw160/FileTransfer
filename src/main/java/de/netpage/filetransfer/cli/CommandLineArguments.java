package de.netpage.filetransfer.cli;

import io.vertx.core.cli.annotations.Description;
import io.vertx.core.cli.annotations.Name;
import io.vertx.core.cli.annotations.Option;
import io.vertx.core.cli.annotations.Summary;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This class provides the Commandline Arguments for the service.
 */
@Name("filetransfer")
@Summary("This tools transfered files via camel-routes.")
public class CommandLineArguments {

    private boolean help;
    private int port = 8080;
    private String routes = "route.properties";

    /**
     * Return true, if the user ask for help.
     *
     * @return TRUE, if help requested
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Set the request for help.
     *
     * @param help TRUE or FALSE
     */
    @Option(shortName = "h", longName = "help", help = true)
    @Description("Displays this help")
    public void setHelp(final boolean help) {
        this.help = help;
    }

    /**
     * Return the Port for the web-server.
     *
     * @return Port for the web-server
     */
    public int getPort() {
        return port;
    }

    @Option(shortName = "p", longName = "port", argName = "port")
    @Description("Set the port for the health check, default: 8080")
    public void setPort(final int port) {
        this.port = port;
    }

    /**
     * Return the name and path for the properties file, which containts the camel routes.
     *
     * @return File to the camel routes
     */
    public String getRoutes() {
        return routes;
    }

    /**
     * Set the name to the camel routes file.
     *
     * @param routes Filename and path
     */
    @Option(shortName = "r", longName = "routes", argName = "/path/to/route.properties")
    @Description("Set the properties file for the routes, default: route.properties")
    public void setRoutes(final String routes) {
        this.routes = routes;
    }

    /**
     * Check if the command line arguments are valid.
     * This method returns true, if all arguments are valid.
     *
     * @return TRUE if valid
     */
    public boolean valid() {
        return StringUtils.isNotBlank(routes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("help", help)
                .append("routes", routes)
                .toString();
    }

}
