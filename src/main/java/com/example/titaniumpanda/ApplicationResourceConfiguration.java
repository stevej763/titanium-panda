package com.example.titaniumpanda;

import com.example.titaniumpanda.domain.PhotoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationResourceConfiguration {

    @Bean
    public PhotoService photoService() {
        return new PhotoService();
    }
}
