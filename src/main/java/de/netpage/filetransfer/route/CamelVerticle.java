package de.netpage.filetransfer.route;

import de.netpage.filetransfer.cli.CommandLineArguments;
import de.netpage.filetransfer.health.HealthRegister;
import de.netpage.filetransfer.health.HealthStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.commons.lang.StringUtils;

import javax.management.*;
import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.util.*;

import static de.netpage.filetransfer.health.HealthStatus.*;

/**
 * This Verticle realized the transfer via the camel routes.
 */
public class CamelVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelVerticle.class);

    private final CamelContext camel;
    private final CamelCheck camelCheck;

    /**
     * Create the Verticle for the Camel Router.
     *
     * @param arguments Arguments from CommandLine
     */
    public CamelVerticle(final CommandLineArguments arguments) {
        LOGGER.debug("Creating CamelVerticle");

        // Create Context for camel
        camel = new DefaultCamelContext();
        camel.addRoutePolicyFactory(new MetricsRoutePolicyFactory());

        // Create HealthCheck for Camel Context
        camelCheck = new CamelCheck();
        HealthRegister.getInstance().add(camelCheck);

        initRouteConfiguration(arguments);
        LOGGER.debug("CamelVerticle created");
    }

    /**
     * Reads the route.properties and build the camel routes.
     *
     * @param arguments Arguments from CommandLine
     */
    private void initRouteConfiguration(final CommandLineArguments arguments) {
        final List<RouteConfiguration> configurations = new ArrayList<>();

        try {
            final Properties prop = new Properties();
            prop.load(new FileInputStream(arguments.getRoutes()));

            final Map<String, RouteConfiguration> map = new HashMap<>();
            for (final Object k : prop.keySet()) {
                final String key = k.toString();
                final String value = prop.get(k).toString();
                if (key.contains(".")) {
                    final String[] split = StringUtils.split(key, ".");

                    RouteConfiguration rc = null;
                    if (map.containsKey(split[0])) {
                        rc = map.get(split[0]);
                    } else {
                        rc = new RouteConfiguration();
                        rc.setRouteId(split[0]);
                        map.put(split[0], rc);
                    }

                    if (StringUtils.equalsIgnoreCase(split[1], "to")) {
                        rc.setTo(value);
                    } else if (StringUtils.equalsIgnoreCase(split[1], "from")) {
                        rc.setFrom(value);
                    }
                }
            }

            configurations.addAll(map.values());

            //**************************************************************************************
//            final RouteConfiguration r1 = new RouteConfiguration();
//            r1.setRouteId("routeFoo");
//            r1.setFrom("direct:foo");
//            r1.setTo("stream:out");
//            configurations.add(r1);
//            final RouteConfiguration r2 = new RouteConfiguration();
//            r2.setRouteId("routeFoo2");
//            r2.setFrom("direct:foo2");
//            r2.setTo("stream:out");
//            configurations.add(r2);
            //**************************************************************************************
        } catch (final Exception e) {
            LOGGER.error("Error while reading routes from {}", arguments.getRoutes());
            LOGGER.fatal("Error while reading", e);
        }

        buildRoutes(configurations);
    }

    /**
     * Build the camel routes from configurations.
     *
     * @param configurations configurations
     */
    private void buildRoutes(final List<RouteConfiguration> configurations) {
        try {
            camel.addRoutes(new RouteBuilder() {
                @Override
                public void configure() {
                    for (final RouteConfiguration rc : configurations) {
                        // Creates the endpoints
                        final Endpoint from = camel.getEndpoint(rc.getFrom());
                        final Endpoint to = camel.getEndpoint(rc.getTo());

                        // Creates the camel route
                        from(from).to(to).setId(rc.getRouteId());

                        // Create new HealthCheck for Router
                        final RouteCheck chk = new RouteCheck();
                        camelCheck.addRoute(chk);
                        chk.setStatus(DEAD);
                        chk.setFrom(rc.getFrom());
                        chk.setTo(rc.getTo());
                        chk.setRouteId(rc.getRouteId());

                        // Debug
                        LOGGER.debug("Creating camel route {} from {} to {}", rc.getRouteId(), rc.getFrom(), rc.getTo());
                    }
                }
            });
        } catch (final Exception e) {
            LOGGER.error("Error while creating the camel routes", e);
            camelCheck.setStatus(ERROR);
        }

        camelCheck.setStatus(DEAD);
    }

    @Override
    public void start() throws Exception {
        if (camelCheck.getStatus() == ERROR) {
            LOGGER.warn("Camel Context contains error - not starting CamelVerticle");
            return;
        }

        LOGGER.debug("Starting CamelVerticle");

        // Register EventNotification
        camel.getManagementStrategy().addEventNotifier(new EventNotifierSupport() {
            @Override
            public void notify(final EventObject event) throws Exception {
                LOGGER.info("Notify for Event: {}", event);
            }

            @Override
            public boolean isEnabled(final EventObject event) {
                return true;
            }

            @Override
            protected void doStart() throws Exception {
                LOGGER.info("Camel routing started");
                camelCheck.setStatus(ALIVE);
            }

            @Override
            protected void doStop() throws Exception {
                LOGGER.info("Camel routing stopped");
                camelCheck.setStatus(DEAD);
            }
        });

        // Starting camel routes
        camel.start();
        camelCheck.setCamelId(camel.getManagedCamelContext().getCamelId());

        // Update CamelHealth every 2s
        vertx.setPeriodic(2000, this::updateHealth);

        //**************************************************************************************
//        vertx.setPeriodic(1000, id -> {
//            final Endpoint endpoint = camel.getEndpoint("direct:foo");
//            final ProducerTemplate template = camel.createProducerTemplate();
//            final Future<Object> future = template.asyncRequestBody(endpoint, "Test1");
//            template.extractFutureBody(future, String.class);
//        });
//        vertx.setPeriodic(2000, id -> {
//            final Endpoint endpoint = camel.getEndpoint("direct:foo2");
//            final ProducerTemplate template = camel.createProducerTemplate();
//            final Future<Object> future = template.asyncRequestBody(endpoint, "Test2");
//            template.extractFutureBody(future, String.class);
//        });
        //**************************************************************************************

        LOGGER.debug("CamelVerticle started");
    }

    private void updateHealth(final Long id) {
        LOGGER.debug("Updating health with {}", id);
        final MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        try {
            // JMX MBean for camel context
            // org.apache.camel:context=camel-1,type=context,name="camel-1"
            // State = Started
            final String camelId = camelCheck.getCamelId();
            final ObjectName camelObjectName = new ObjectName("org.apache.camel:context=" + camelId + ",type=context,name=\"" + camelId + "\"");
            camelCheck.setStatus(getJmxState(server, camelObjectName));

            // JMX MBean for route
            // org.apache.camel:context=camel-1,type=routes,name="route1"
            for (final RouteCheck rc : camelCheck.getRoutes()) {
                final String routeId = rc.getRouteId();
                final ObjectName routeObjectName = new ObjectName("org.apache.camel:context=" + camelId + ",type=routes,name=\"" + routeId + "\"");
                rc.setStatus(getJmxState(server, routeObjectName));
                rc.setExchangesCompleted(getJmxData(server, routeObjectName, "getExchangesCompleted", Long.class));
                rc.setExchangesFailed(getJmxData(server, routeObjectName, "getExchangesFailed", Long.class));
                rc.setExchangesInflight(getJmxData(server, routeObjectName, "getExchangesInflight", Long.class));
                rc.setExchangesTotal(getJmxData(server, routeObjectName, "getExchangesTotal", Long.class));

                rc.setLastExchangeCompletedTimestamp(getJmxData(server, routeObjectName, "getLastExchangeCompletedTimestamp", Date.class));
                rc.setLastExchangeFailureTimestamp(getJmxData(server, routeObjectName, "getLastExchangeFailureTimestamp", Date.class));
            }
        } catch (final Exception e) {
            LOGGER.warn("Error while updating camel health", e);
        }
    }

    private <T> T getJmxData(final MBeanServer server, final ObjectName objectName, final String operationName, final Class<T> clazz) throws MBeanException, InstanceNotFoundException, ReflectionException {
        final Object data = server.invoke(objectName, operationName, null, null);
        if (data != null && clazz.isInstance(data)) {
            return (T) data;
        } else if (clazz.isInstance(data)) {
            LOGGER.warn("Data {} not an instance of {}", data, clazz);
            return null;
        } else {
            return null;
        }
    }

    private HealthStatus getJmxState(final MBeanServer server, final ObjectName objectName) throws InstanceNotFoundException, MBeanException, ReflectionException {
        final Object camelState = server.invoke(objectName, "getState", null, null);
        if (camelState != null && StringUtils.equalsIgnoreCase("Started", camelState.toString())) {
            return ALIVE;
        } else {
            return DEAD;
        }
    }

    @Override
    public void stop() throws Exception {
        LOGGER.debug("Stopping CamelVerticle");
        LOGGER.debug("CamelVerticle stopped");
    }

}
