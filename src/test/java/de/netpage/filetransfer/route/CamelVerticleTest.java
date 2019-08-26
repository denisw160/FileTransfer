package de.netpage.filetransfer.route;

import de.netpage.filetransfer.cli.CommandLineArguments;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Testcases for {@link CamelVerticle}.
 */
@RunWith(VertxUnitRunner.class)
public class CamelVerticleTest {

    private Vertx vertx;

    @Before
    public void setUp(final TestContext context) {
        final CommandLineArguments args = new CommandLineArguments();
        args.setRoutes("./src/main/resources/route-sample.properties");

        vertx = Vertx.vertx();
        vertx.deployVerticle(new CamelVerticle(args), context.asyncAssertSuccess());
    }

    @After
    public void tearDown(final TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testRoute() throws Exception {
        final File doneDir = new File("./src/sample/in/.done");
        final File[] doneFiles = doneDir.listFiles((dir, name) -> name.endsWith(".tmp"));
        assertNotNull(doneFiles);
        for (final File in : doneFiles) {
            final boolean delete = in.delete();
            assertTrue(delete);
        }

        final File inDir = new File("./src/sample/in");
        final File[] inFiles = inDir.listFiles((dir, name) -> name.endsWith(".tmp"));
        assertNotNull(inFiles);
        for (final File in : inFiles) {
            final boolean delete = in.delete();
            assertTrue(delete);
        }

        final File outDir = new File("./src/sample/out");
        final File[] outFiles = outDir.listFiles((dir, name) -> name.endsWith(".tmp"));
        assertNotNull(outFiles);
        for (final File out : outFiles) {
            final boolean delete = out.delete();
            assertTrue(delete);
        }

        try {
            final PrintWriter writer = new PrintWriter(inDir.getAbsolutePath() + "/sample.tmp", "UTF-8");
            writer.println("The first line");
            writer.println("The second line");
            writer.close();
        } catch (final IOException e) {
            // do something
        }

        Thread.sleep(5000);

        final File doneFile = new File(doneDir.getAbsolutePath() + "/sample.tmp");
        assertTrue(doneFile.exists());
        assertTrue(doneFile.isFile());

        final File outFile = new File(outDir.getAbsolutePath() + "/sample.tmp");
        assertTrue(outFile.exists());
        assertTrue(outFile.isFile());
    }

}