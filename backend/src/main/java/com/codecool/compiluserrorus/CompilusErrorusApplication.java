package com.codecool.compiluserrorus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class CompilusErrorusApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompilusErrorusApplication.class, args);
    }

    @Profile("production")
    public void init() {

    }

}
