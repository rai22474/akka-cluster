package io.wasupu.connections.domain;

public class NodeStatus {

    public NodeStatus(String id,
                      Integer numberOfConnections) {
        this.id = id;
        this.numberOfConnections = numberOfConnections;
    }

    public Integer getNumberOfConnections() {
        return numberOfConnections;
    }

    public String getId() {
        return id;
    }

    private String id;

    private Integer numberOfConnections;
}
