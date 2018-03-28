package io.wasupu;

public class ClusterStatus {


    public ClusterStatus(String id,
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
