package de.netpage.filetransfer.health;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is an singleton class for registation of health checks.
 */
public class HealthRegister {

    /**
     * Singleton instance of this class.
     */
    private static final HealthRegister instance = new HealthRegister();

    private final List<HealthCheck> checks = new ArrayList<>();

    private HealthRegister() {
        // Singleton
    }

    /**
     * Return the singleton instance.
     *
     * @return Instance
     */
    public static HealthRegister getInstance() {
        return instance;
    }

    /**
     * Adds a check.
     *
     * @param check {@link HealthCheck}
     */
    public void add(final HealthCheck check) {
        checks.add(check);
    }

    /**
     * Remove the check.
     *
     * @param check {@link HealthCheck}
     */
    public void remove(final HealthCheck check) {
        checks.remove(check);
    }

    /**
     * Returns a list with all checks.
     *
     * @return List of {@link HealthCheck}s.
     */
    public List<HealthCheck> getAllChecks() {
        return Collections.unmodifiableList(checks);
    }

}
