package org.mritunjd.sample;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducer {
    private KafkaSender<Object, String> kafkaSender;

    public KafkaProducer() {
        this.kafkaSender = init();
    }

    public Flux<SenderResult<Object>> send(String payload) {
        ProducerRecord<Object, String> record = new ProducerRecord<>("test-topic", payload);
        SenderRecord<Object, String, Object> senderRecord = SenderRecord.create(record, null);
        return kafkaSender.send(Mono.just(senderRecord));
    }

    private KafkaSender<Object, String> init() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS"));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "sample-producer");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        SenderOptions<Object, String> senderOptions = SenderOptions.create(props);
        return KafkaSender.create(senderOptions);
    }
}
