package de.netpage.filetransfer.route;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This is the configuration of a route.
 */
public class RouteConfiguration {

    private String routeId;

    private String from;
    private String to;

    /**
     * Get the route id.
     *
     * @return RouteId
     */
    public String getRouteId() {
        return routeId;
    }

    /**
     * Set the route id.
     *
     * @param routeId RouteId
     */
    public void setRouteId(final String routeId) {
        this.routeId = routeId;
    }

    /**
     * Get the From-Url of the route.
     *
     * @return From-Url (Camel)
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set the From-Url of the route.
     *
     * @param from From-Url (Camel)
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Get the To-Url of the route.
     *
     * @return To-Url (Camel)
     */
    public String getTo() {
        return to;
    }

    /**
     * Set the To-Url of the route.
     *
     * @param to To-Url (Camel)
     */
    public void setTo(final String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("routeId", routeId)
                .append("from", from)
                .append("to", to)
                .toString();
    }

}
