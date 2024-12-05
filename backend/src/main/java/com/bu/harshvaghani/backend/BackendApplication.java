package com.bu.harshvaghani.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        System.out.println("This is the Spring Boot Application");
        SpringApplication.run(BackendApplication.class, args);
    }

}
