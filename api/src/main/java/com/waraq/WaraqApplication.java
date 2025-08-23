package com.waraq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WaraqApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaraqApplication.class, args);
    }
}
