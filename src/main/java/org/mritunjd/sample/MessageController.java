package org.mritunjd.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaReceiver<Object, String> kafkaReceiver;

    @PostMapping("/add/{message}")
    public Mono<Void> addMessage(@PathVariable String message) {
        return kafkaProducer.send(message).then();
    }

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getAllMessages() {
        return kafkaReceiver
                .receive()
                .log()
                .doOnNext(r -> r.receiverOffset().acknowledge())
                .map(ReceiverRecord::value);
    }
}
