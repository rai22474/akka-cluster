package io.wasupu.connections.domain;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ddata.*;
import akka.cluster.ddata.Replicator.Changed;
import akka.cluster.ddata.Replicator.Subscribe;
import akka.cluster.ddata.Replicator.Update;
import akka.cluster.ddata.Replicator.WriteAll;
import scala.concurrent.duration.Duration;

import java.util.Set;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;

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
                .match(IncreaseConnections.class, increaseConnections -> {
                    this.numberOfConnections++;
                    updateNumberOfConnections();
                })
                .match(DecreaseConnections.class, decreaseConnections -> {
                    this.numberOfConnections--;
                    updateNumberOfConnections();
                })
                .match(GetStatus.class, getStatus -> getSender().tell(new ConnectionsStatus(id, numberOfConnections), getSelf()))
                .match(Changed.class, changed -> changed.key().equals(dataKey),
                        changed -> receiveChanged((Replicator.Changed<ORMultiMap<String,Integer>>) changed))
                .build();
    }

    @Override
    public void preStart() {
        var subscribe = new Subscribe<>(dataKey, getSelf());
        replicator.tell(subscribe, ActorRef.noSender());
    }

    private void receiveChanged(Replicator.Changed<ORMultiMap<String,Integer>> c) {
        var data = c.dataValue();
        System.out.println(node.state());
        System.out.println("***************************************************************************");
        System.out.println("Current elements: {}" + data);
        System.out.println("***************************************************************************");
    }

    private void updateNumberOfConnections() {
        final var writeAll = new WriteAll(Duration.create(5, SECONDS));

        var update = new Update<>(
                dataKey,
                ORMultiMap.create(),
                writeAll,
                curr -> curr.put(node, id, Set.of(numberOfConnections)));

        replicator.tell(update, getSelf());
    }

    private Integer numberOfConnections = 0;

    private String id = UUID.randomUUID().toString();

    private final ActorRef replicator =
            DistributedData.get(getContext().getSystem()).replicator();

    private final Cluster node = Cluster.get(getContext().getSystem());

    private final Key<ORMultiMap<String, Integer>> dataKey = ORMultiMapKey.create("numberOfConnections");
}
