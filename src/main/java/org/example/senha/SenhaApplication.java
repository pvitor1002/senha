package org.example.senha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SenhaApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SenhaApplication.class);
        app.run(args);
    }
}
