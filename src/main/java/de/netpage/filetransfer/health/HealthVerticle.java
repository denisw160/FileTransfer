package de.netpage.filetransfer.health;

import de.netpage.filetransfer.cli.CommandLineArguments;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.Map;

import static de.netpage.filetransfer.health.HealthStatus.ALIVE;
import static io.vertx.core.http.HttpHeaders.CACHE_CONTROL;
import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import static io.vertx.core.http.HttpMethod.GET;

/**
 * This Verticle realized the health check.
 */
public class HealthVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthVerticle.class);

    private final int port;

    public HealthVerticle(final CommandLineArguments arguments) {
        this.port = arguments.getPort();
    }

    @Override
    public void start() throws Exception {
        LOGGER.debug("Starting HealthVerticle");

        final Router router = Router.router(vertx);
        router.route(GET, "/health")
                .produces("application/json")
                .handler(this::healthRest);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(port);

        LOGGER.debug("HealthVerticle started");
    }

    @Override
    public void stop() throws Exception {
        LOGGER.debug("Stopping HealthVerticle");
        LOGGER.debug("HealthVerticle stopped");
    }

    private void healthRest(final RoutingContext context) {
        HealthStatus systemStatus = ALIVE;

        final Map<String, Object> body = new HashMap<>();
        for (final HealthCheck c : HealthRegister.getInstance().getAllChecks()) {
            body.put(c.getName(), c);
            systemStatus = systemStatus.with(c.getStatus());
        }

        body.put("status", systemStatus);
        context.request()
                .response()
                .putHeader(CONTENT_TYPE, context.getAcceptableContentType())
                .putHeader(CACHE_CONTROL, "no-cache")
                .end(new JsonObject(body).encode());
    }

}
