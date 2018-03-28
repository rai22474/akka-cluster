package io.wasupu;

import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfiguration {

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("akka-cluster");
    }

    @Autowired
    private ApplicationContext applicationContext;

}
