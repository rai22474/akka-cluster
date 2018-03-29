package io.wasupu;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        String[] ports = Arrays
                .stream(args)
                .filter(param -> !param.contains("--server.port"))
                .toArray(String[]::new);

        if (ports.length == 0) {
            startup(new String[]{"2551", "2552", "0"});
        } else {
            startup(ports);
        }
    }

    public static void startup(String[] ports) {
        Arrays.stream(ports).forEach(port -> {
            // Override the configuration of the port
            var config = ConfigFactory.parseString(
                    "akka.remote.netty.tcp.port=" + port + "\n" +
                            "akka.remote.artery.canonical.port=" + port)
                    .withFallback(ConfigFactory.load());

            // Create an Akka system
            var system = ActorSystem.create("ClusterSystem", config);

            // Create an actor that handles cluster domain events
            system.actorOf(Props.create(SimpleClusterListener.class), "clusterListener");
        });
    }
}
