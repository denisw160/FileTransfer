package de.netpage.filetransfer.health;

import de.netpage.filetransfer.cli.CommandLineArguments;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testcases for {@link HealthVerticle}.
 */
@RunWith(VertxUnitRunner.class)
public class HealthVerticleTest {

    /**
     * Port for the web-server
     */
    private final int port = 8081;

    private Vertx vertx;

    @Before
    public void setUp(final TestContext context) {
        final CommandLineArguments args = new CommandLineArguments();
        args.setPort(port);
        args.setHelp(false);
        args.setRoutes("none");

        vertx = Vertx.vertx();
        vertx.deployVerticle(new HealthVerticle(args), context.asyncAssertSuccess());
    }

    @After
    public void tearDown(final TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testHealth(final TestContext context) throws Exception {
        final Async async = context.async();

        vertx.createHttpClient().getNow(port, "localhost", "/health",
                response -> response.handler(body -> {
                    context.assertTrue(body.toString().contains("status"));
                    async.complete();
                }));
    }

}