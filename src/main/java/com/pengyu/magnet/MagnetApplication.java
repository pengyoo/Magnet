package com.pengyu.magnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MagnetApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagnetApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate template = new RestTemplate();
        //customize
        return template;
    }

}
