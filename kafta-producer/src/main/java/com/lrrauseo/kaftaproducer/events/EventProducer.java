package com.lrrauseo.kaftaproducer.events;

import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

@RequiredArgsConstructor
@Slf4j
public class EventProducer {

  public EventProducer() {
    super();
    producer = createProducer();
  }

  private final Producer<String, String> producer;

  private Producer<String, String> createProducer() {
    if (producer != null) return producer;
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    );
    props.put(
      "value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    );
    props.put("serializer.class", "kafka.serializer.DefaultEncoder");

    return new KafkaProducer<String, String>(props);
  }

  public void execute() {
    String keyString = UUID.randomUUID().toString();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    String message = sdf.format(new java.util.Date());

    message += "|" + keyString;
    message += "|" + "NEW_MESSAGE";

    log.info("***** Starting message send *************");
    ProducerRecord<String, String> record = new ProducerRecord<String, String>(
      "EventRegister",
      keyString,
      message
    );
    producer.send(record);
    producer.flush();
    producer.close();
    log.info("**** Message send complete [{}]", message);
  }
}
