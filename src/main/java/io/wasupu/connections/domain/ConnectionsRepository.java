package io.wasupu.connections.domain;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.PatternsCS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

import static io.wasupu.connections.domain.ConnectionsStatusActor.*;

@Component
public class ConnectionsRepository {
    public void add() {
        clusterStatus.tell(new IncreaseConnections(), ActorRef.noSender());
    }

    public void delete() {
        clusterStatus.tell(new DecreaseConnections(), ActorRef.noSender());
    }

    public ConnectionsStatus get() throws ExecutionException, InterruptedException {
        return (ConnectionsStatus) PatternsCS.ask(clusterStatus, new GetStatus(), 1000)
                .toCompletableFuture().get();
    }

    @PostConstruct
    public void createClusterStatusActor() {
        clusterStatus =
                actorSystem.actorOf(ConnectionsStatusActor.props(), "clusterStatus");
    }

    private ActorRef clusterStatus;

    @Autowired
    private ActorSystem actorSystem;


}
