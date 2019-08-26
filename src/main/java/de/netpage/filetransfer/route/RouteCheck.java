package de.netpage.filetransfer.route;

import de.netpage.filetransfer.health.HealthCheck;
import de.netpage.filetransfer.health.HealthStatus;
import de.netpage.filetransfer.utils.DateUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

/**
 * This class displays the status of a camel route.
 */
public class RouteCheck implements HealthCheck {

    private String name;
    private HealthStatus status;

    private String from;
    private String to;

    private Long exchangesCompleted;
    private Long exchangesFailed;
    private Long exchangesInflight;
    private Long exchangesTotal;

    private String lastExchangeCompletedTimestamp;
    private String lastExchangeFailureTimestamp;

    /**
     * Get the name or route id for the route.
     *
     * @return RouteId
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Set the name or route id for the route.
     *
     * @param name RouteId
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the name or route id for the route.
     *
     * @return RouteId
     */
    public String getRouteId() {
        return getName();
    }

    /**
     * Set the name or route id for the route.
     *
     * @param routeId RouteId
     */
    public void setRouteId(final String routeId) {
        setName(routeId);
    }

    /**
     * Get the status of the route.
     *
     * @return Status
     */
    @Override
    public HealthStatus getStatus() {
        return status;
    }

    /**
     * Set the status of the route.
     *
     * @param status Status
     */
    public void setStatus(final HealthStatus status) {
        this.status = status;
    }

    /**
     * Return the from of the route.
     *
     * @return From (Camel-Url)
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set the from of the route.
     *
     * @param from From (Camel-Url)
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Return the to of the route.
     *
     * @return To (Camel-Url)
     */
    public String getTo() {
        return to;
    }

    /**
     * Set the to of the route.
     *
     * @param to To (Camel-Url)
     */
    public void setTo(final String to) {
        this.to = to;
    }

    /**
     * Get the count of exchanges completed.
     *
     * @return count
     */
    public Long getExchangesCompleted() {
        return exchangesCompleted;
    }

    /**
     * Set the count of exchanges completed.
     *
     * @param exchangesCompleted count
     */
    public void setExchangesCompleted(final Long exchangesCompleted) {
        this.exchangesCompleted = exchangesCompleted;
    }

    /**
     * Get the count of exchanges failed.
     *
     * @return count
     */
    public Long getExchangesFailed() {
        return exchangesFailed;
    }

    /**
     * Set the count of exchanges failed.
     *
     * @param exchangesFailed count
     */
    public void setExchangesFailed(final Long exchangesFailed) {
        this.exchangesFailed = exchangesFailed;
    }

    /**
     * Get the count of exchanges in progress.
     *
     * @return count
     */
    public Long getExchangesInflight() {
        return exchangesInflight;
    }

    /**
     * Set the count of exchanges in progress.
     *
     * @param exchangesInflight count
     */
    public void setExchangesInflight(final Long exchangesInflight) {
        this.exchangesInflight = exchangesInflight;
    }

    /**
     * Get the count of exchanges total.
     *
     * @return count
     */
    public Long getExchangesTotal() {
        return exchangesTotal;
    }

    /**
     * Set the count of exchanges total.
     *
     * @param exchangesTotal count
     */
    public void setExchangesTotal(final Long exchangesTotal) {
        this.exchangesTotal = exchangesTotal;
    }

    /**
     * Return the last timestamp of an exchange is completed.
     *
     * @return timestamp as String
     */
    public String getLastExchangeCompletedTimestamp() {
        return lastExchangeCompletedTimestamp;
    }

    /**
     * Set the last timestamp of an exchange is completed.
     *
     * @param lastExchangeCompletedTimestamp timestamp as String
     */
    public void setLastExchangeCompletedTimestamp(final String lastExchangeCompletedTimestamp) {
        this.lastExchangeCompletedTimestamp = lastExchangeCompletedTimestamp;
    }

    /**
     * Set the last timestamp of an exchange is completed.
     *
     * @param lastExchangeCompletedTimestamp timestamp as Date
     */
    public void setLastExchangeCompletedTimestamp(final Date lastExchangeCompletedTimestamp) {
        setLastExchangeCompletedTimestamp(DateUtils.convertTimestamp(lastExchangeCompletedTimestamp));
    }

    /**
     * Return the last timestamp of an exchange is failed.
     *
     * @return timestamp as String
     */
    public String getLastExchangeFailureTimestamp() {
        return lastExchangeFailureTimestamp;
    }

    /**
     * Set the last timestamp of an exchange is failed.
     *
     * @param lastExchangeFailureTimestamp timestamp as String
     */
    public void setLastExchangeFailureTimestamp(final String lastExchangeFailureTimestamp) {
        this.lastExchangeFailureTimestamp = lastExchangeFailureTimestamp;
    }

    /**
     * Set the last timestamp of an exchange is failed.
     *
     * @param lastExchangeFailureTimestamp timestamp as Date
     */
    public void setLastExchangeFailureTimestamp(final Date lastExchangeFailureTimestamp) {
        setLastExchangeFailureTimestamp(DateUtils.convertTimestamp(lastExchangeFailureTimestamp));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("status", status)
                .append("from", from)
                .append("to", to)
                .append("exchangesCompleted", exchangesCompleted)
                .append("exchangesFailed", exchangesFailed)
                .append("exchangesInflight", exchangesInflight)
                .append("exchangesTotal", exchangesTotal)
                .append("lastExchangeCompletedTimestamp", lastExchangeCompletedTimestamp)
                .append("lastExchangeFailureTimestamp", lastExchangeFailureTimestamp)
                .toString();
    }

}
