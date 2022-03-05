package com.titaniumpanda.app;

import com.titaniumpanda.app.dao.PhotoDao;
import com.titaniumpanda.app.domain.PhotoFactory;
import com.titaniumpanda.app.domain.PhotoService;
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
