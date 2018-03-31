package io.wasupu;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ActorSystemConfiguration {

    @Bean
    public ActorSystem actorSystem() {
        var config = ConfigFactory.parseString(
                "akka.remote.netty.tcp.port=" + clusterPort + "\n" +
                        "akka.remote.artery.canonical.port=" + clusterPort)
                .withFallback(ConfigFactory.load());

        var actorSystem = ActorSystem.create("akka-cluster", config);
        // Create an actor that handles cluster domain events
        actorSystem.actorOf(Props.create(SimpleClusterListener.class), "clusterListener");

        return actorSystem;
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${cluster.port}")
    private Integer clusterPort;


}
