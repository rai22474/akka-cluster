package io.wasupu.connections.domain;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClusterStatus {

    public Collection<NodeStatus> getNodes() {
        return nodes.values();
    }

    public void refreshNode(NodeStatus nodeStatus) {
        nodes.put(nodeStatus.getId(), nodeStatus);
    }

    private Map<String, NodeStatus> nodes = new ConcurrentHashMap<>();
}
