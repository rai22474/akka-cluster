package io.wasupu.connections.domain;

import akka.actor.AbstractActor;
import akka.actor.Props;

import java.util.UUID;

public class ConnectionsStatusActor extends AbstractActor {

    public static Props props() {
        return Props.create(ConnectionsStatusActor.class);
    }

    static public class IncreaseConnections {
    }

    static public class DecreaseConnections {
    }

    static public class GetStatus {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(IncreaseConnections.class, increaseConnections -> this.numberOfConnections++)
                .match(DecreaseConnections.class, decreaseConnections -> this.numberOfConnections--)
                .match(GetStatus.class, getStatus -> getSender().tell(new ConnectionsStatus(id, numberOfConnections), getSelf()))
                .build();
    }

    private Integer numberOfConnections = 0;

    private String id = UUID.randomUUID().toString();
}
