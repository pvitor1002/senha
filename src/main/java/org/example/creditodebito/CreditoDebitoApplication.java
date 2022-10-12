package org.example.creditodebito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CreditoDebitoApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CreditoDebitoApplication.class);
        app.run(args);
    }
}
