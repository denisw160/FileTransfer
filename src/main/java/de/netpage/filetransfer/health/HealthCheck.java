package de.netpage.filetransfer.health;

/**
 * This is an generic interface for all health checks.
 */
public interface HealthCheck {

    /**
     * Return the name of the health-check.
     *
     * @return Name
     */
    String getName();

    /**
     * Returns the Status of the check.
     *
     * @return Status
     */
    HealthStatus getStatus();

}
