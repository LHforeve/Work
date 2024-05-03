package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Redis1Application {

    public static void main(String[] args) {
        SpringApplication.run(Redis1Application.class, args);
    }

}
