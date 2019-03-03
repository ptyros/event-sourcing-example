package com.paris.eventsourcingexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class EventSourcingExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventSourcingExampleApplication.class, args);
    }

}
