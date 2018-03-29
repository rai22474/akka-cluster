package io.wasupu;

import io.wasupu.connections.rest.ConnectionsRest;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(ConnectionsRest.class);

    }
}