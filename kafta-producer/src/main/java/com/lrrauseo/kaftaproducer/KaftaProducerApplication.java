package com.lrrauseo.kaftaproducer;

import com.lrrauseo.kaftaproducer.events.EventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@Slf4j
public class KaftaProducerApplication {

  public static void main(String[] args) {
    //SpringApplication.run(KaftaProducerApplication.class, args);
    KaftaProducerApplication app = new KaftaProducerApplication();
    app.start();
  }

  private void start() {
    log.info("Start the Application");
    EventProducer eventProducer = new EventProducer();
    eventProducer.execute();
  }
}
