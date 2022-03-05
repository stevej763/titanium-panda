package com.titaniumpanda.app;

import com.titaniumpanda.app.dao.CategoryDao;
import com.titaniumpanda.app.dao.PhotoDao;
import com.titaniumpanda.app.domain.CategoryFactory;
import com.titaniumpanda.app.domain.CategoryService;
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
    public CategoryService categoryService() {
        return new CategoryService(categoryDao(), categoryFactory());
    }

    @Bean
    public PhotoFactory photoFactory() {
        return new PhotoFactory();
    }

    @Bean
    public PhotoDao photoDao() {
        return new PhotoDao();
    }

    @Bean
    public CategoryFactory categoryFactory() {
        return new CategoryFactory();
    }


    @Bean
    public CategoryDao categoryDao() {
        return new CategoryDao();
    }

}
