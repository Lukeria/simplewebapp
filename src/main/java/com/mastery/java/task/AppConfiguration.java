package com.mastery.java.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AppConfiguration extends SpringBootServletInitializer {

    private final String api_id="8342350";
    private final String api_hash="41b32e4b4bb9cfd35af464be93819645";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(AppConfiguration.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(AppConfiguration.class, args);
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            String quote = restTemplate.getForObject(
                    "https://quoters.apps.pcfone.io/api/random", String.class);

        };
    }
}
