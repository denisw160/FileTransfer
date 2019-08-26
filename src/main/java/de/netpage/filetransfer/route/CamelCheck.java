package de.netpage.filetransfer.route;

import de.netpage.filetransfer.health.HealthCheck;
import de.netpage.filetransfer.health.HealthStatus;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class displays the status of camel router.
 * A router contains multiple camel routes.
 */
public class CamelCheck implements HealthCheck {

    private String name;
    private HealthStatus status;

    private final List<RouteCheck> routes = new ArrayList<>();

    /**
     * Returns the name or camel id.
     *
     * @return CamelId
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name or the camel id.
     *
     * @param name CamelId
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns the name or camel id.
     *
     * @return CamelId
     */
    public String getCamelId() {
        return getName();
    }

    /**
     * Set the name or the camel id.
     *
     * @param camelId CamelId
     */
    public void setCamelId(final String camelId) {
        setName(camelId);
    }

    /**
     * Return the status for the camel router.
     * The router and the routes have own states. This
     * method aggregates all states.
     *
     * @return Aggregation of all states
     */
    @Override
    public HealthStatus getStatus() {
        HealthStatus fullStatus = status;
        for (final RouteCheck rc : getRoutes()) {
            fullStatus = fullStatus.with(rc.getStatus());
        }
        return fullStatus;
    }

    /**
     * Set the status of the camel route.
     *
     * @param status Status
     */
    public void setStatus(final HealthStatus status) {
        this.status = status;
    }

    /**
     * Return the list over all routes.
     * The list in unmodifiable.
     *
     * @return Unmodifiable list of all route checks
     */
    public List<RouteCheck> getRoutes() {
        return Collections.unmodifiableList(routes);
    }

    /**
     * Adds a {@link RouteCheck}.
     *
     * @param check RouteCheck
     */
    public void addRoute(final RouteCheck check) {
        routes.add(check);
    }

    /**
     * Remove the {@link RouteCheck}.
     *
     * @param check RouteCheck
     */
    public void removeRoute(final RouteCheck check) {
        routes.remove(check);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("status", status)
                .toString();
    }

}
