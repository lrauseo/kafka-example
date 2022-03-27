package com.lrrauseo.kafkaconsumer.event;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

@RequiredArgsConstructor
@Slf4j
public class ConsumerEvent {
  private final KafkaConsumer<String, String> consumer;

  public ConsumerEvent() {
    super();
    consumer = createConsumer();
  }

  private KafkaConsumer<String, String> createConsumer() {
    if (consumer != null) {
      return consumer;
    }
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put(
      "key.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer"
    );
    props.put(
      "value.deserializer",
      "org.apache.kafka.common.serialization.StringDeserializer"
    );
    props.put("group.id", "default");
    return new KafkaConsumer<String, String>(props);
  }

  public void execute() {
    List<String> topics = new ArrayList<>();
    topics.add("EventRegister");
    consumer.subscribe(topics);

    log.info("Starting to consume messages");
    boolean continuar = true;
    while (continuar) {
      try {
        ConsumerRecords<String, String> records = consumer.poll(
          Duration.ofMillis(100)
        );
        for (var record : records) {
          saveMessage(record.topic(), record.partition(), record.value());
          if (record.value().equals("STOP")) {
            continuar = false;
          }
        }
      } catch (Exception e) {
        log.error("Error consuming message", e);
      }
    }

    consumer.close();
  }

  private void saveMessage(String topic, int partition, String message) {
    log.info(
      "Consumed message Topic:{}, Partition:{}, message:{}: " + topic,
      partition,
      message
    );
  }
}
