package com.lrrauseo.kafkaconsumer;

import com.lrrauseo.kafkaconsumer.event.ConsumerEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.event.ConsumerPausedEvent;

@SpringBootApplication
@Slf4j
public class KafkaConsumerApplication {

  public static void main(String[] args) {
    //SpringApplication.run(KafkaConsumerApplication.class, args);
    KafkaConsumerApplication app = new KafkaConsumerApplication();
    app.start();
  }

  private void start() {
    log.info("Start the Application");
    ConsumerEvent consumerEvent = new ConsumerEvent();
    consumerEvent.execute();
  }
}
