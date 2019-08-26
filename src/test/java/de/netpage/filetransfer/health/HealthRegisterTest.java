package de.netpage.filetransfer.health;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Testcases for {@link HealthRegister}.
 */
public class HealthRegisterTest {

    @Test
    public void getInstance() throws Exception {
        assertNotNull(HealthRegister.getInstance());
    }

    @Test
    public void add() throws Exception {
        final HealthRegister instance = HealthRegister.getInstance();
        final TestCheck chk = new TestCheck();

        assertFalse(instance.getAllChecks().contains(chk));
        instance.add(chk);
        assertTrue(instance.getAllChecks().contains(chk));
    }

    @Test
    public void remove() throws Exception {
        final HealthRegister instance = HealthRegister.getInstance();
        final TestCheck chk = new TestCheck();

        instance.add(chk);
        assertTrue(instance.getAllChecks().contains(chk));

        instance.remove(chk);
        assertFalse(instance.getAllChecks().contains(chk));
    }

    @Test
    public void getAllChecks() throws Exception {
        final HealthRegister instance = HealthRegister.getInstance();
        assertEquals(0, instance.getAllChecks().size());

        final TestCheck chk = new TestCheck();
        instance.add(chk);
        assertTrue(instance.getAllChecks().contains(chk));
        assertEquals(1, instance.getAllChecks().size());

        instance.remove(chk);
        assertFalse(instance.getAllChecks().contains(chk));
        assertEquals(0, instance.getAllChecks().size());
    }

    /**
     * This class is only for testing.
     */
    private static final class TestCheck implements HealthCheck {
        @Override
        public String getName() {
            return "Test";
        }

        @Override
        public HealthStatus getStatus() {
            return HealthStatus.ALIVE;
        }
    }

}