package io.wasupu.connections.domain;

public class ConnectionsStatus {


    public ConnectionsStatus(String id,
                             Integer numberOfConnections) {
        this.id = id;
        this.numberOfConnections = numberOfConnections;
    }

    private String id;

    public Integer getNumberOfConnections() {
        return numberOfConnections;
    }

    public String getId() {
        return id;
    }

    private Integer numberOfConnections;
}
