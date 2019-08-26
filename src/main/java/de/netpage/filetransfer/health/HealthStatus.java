package de.netpage.filetransfer.health;

/**
 * {@code HealthStatus} is the result of a health check in {@link
 * HealthVerticle}.
 */
public enum HealthStatus {

    /**
     * The service is good.
     */
    ALIVE('*'),
    /**
     * The service is unusable.
     */
    DEAD('!'),
    /**
     * Unable to validate the services.
     */
    ERROR('?');

    private final char mark;

    HealthStatus(final char mark) {
        this.mark = mark;
    }

    public HealthStatus with(final HealthStatus that) {
        return that.ordinal() > ordinal() ? that : this;
    }

    /**
     * Returns a single character representing service health status, suitable
     * for {@code text/plain} responses.
     *
     * @return the mark representing health
     */
    public char mark() {
        return mark;
    }
}
