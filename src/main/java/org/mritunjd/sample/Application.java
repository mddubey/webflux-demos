package org.mritunjd.sample;

import org.mritunjd.sample.eventstream.Event;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@SpringBootApplication
@Configuration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner initData(MongoOperations mongo) {
        return (String... args) -> {

            mongo.dropCollection(Event.class);
            mongo.createCollection(Event.class, CollectionOptions.empty().size(1000000).capped());

            Flux.range(1, 5)
                    .map(i -> new Event(i.longValue(), UUID.randomUUID().toString()))
                    .map(mongo::save)
                    .blockLast(Duration.ofSeconds(5));
        };
    }

}
