package io.wasupu;

import akka.actor.AbstractActor;
import akka.actor.Props;

import java.util.UUID;

public class ClusterStatusActor extends AbstractActor {

    public static Props props() {
        return Props.create(ClusterStatusActor.class);
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
                .match(GetStatus.class, getStatus -> getSender().tell(new ClusterStatus(id, numberOfConnections), getSelf()))
                .build();
    }

    private Integer numberOfConnections = 0;

    private String id = UUID.randomUUID().toString();
}
