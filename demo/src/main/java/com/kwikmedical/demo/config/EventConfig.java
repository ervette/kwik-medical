package com.kwikmedical.demo.config;

import com.kwikmedical.demo.events.EventBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    public EventBroker eventBroker() {
        return new EventBroker();
    }
}
