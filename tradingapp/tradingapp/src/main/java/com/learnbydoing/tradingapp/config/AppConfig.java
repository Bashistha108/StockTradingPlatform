package com.learnbydoing.tradingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // RestTemplate simplifies the process of making HTTP requests and handling respomses. Automatically
    // maps the responses to Java objects or raw strings (JSON into Java object using Jackson)
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
