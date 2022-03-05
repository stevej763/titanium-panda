package com.example.titaniumpanda;

import com.example.titaniumpanda.dao.PhotoDao;
import com.example.titaniumpanda.domain.PhotoFactory;
import com.example.titaniumpanda.domain.PhotoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationResourceConfiguration {

    @Bean
    public PhotoService photoService() {
        return new PhotoService(photoFactory(), photoDao());
    }

    @Bean
    public PhotoFactory photoFactory() {
        return new PhotoFactory();
    }

    @Bean
    public PhotoDao photoDao() {
        return new PhotoDao();
    }
}
