package io.wasupu;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

import static akka.pattern.PatternsCS.ask;

import static io.wasupu.ClusterStatusActor.*;

@Component
public class ConnectionsRepository {
    public void add() {
        clusterStatus.tell(new IncreaseConnections(), ActorRef.noSender());
    }

    public void delete() {
        clusterStatus.tell(new DecreaseConnections(), ActorRef.noSender());
    }

    public ClusterStatus get() throws ExecutionException, InterruptedException {
        return (ClusterStatus) ask(clusterStatus, new GetStatus(), 1000)
                .toCompletableFuture().get();
    }

    @PostConstruct
    public void createClusterStatusActor() {
        clusterStatus =
                actorSystem.actorOf(props(), "clusterStatus");
    }

    private ActorRef clusterStatus;

    @Autowired
    private ActorSystem actorSystem;


}
