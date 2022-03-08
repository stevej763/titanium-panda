package com.titaniumpanda.app;

import com.titaniumpanda.app.domain.CategoryFactory;
import com.titaniumpanda.app.domain.CategoryService;
import com.titaniumpanda.app.domain.PhotoFactory;
import com.titaniumpanda.app.domain.PhotoService;
import com.titaniumpanda.app.repository.CategoryRepository;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationResourceConfiguration {

    @Autowired
    PhotoRepository photoRepository;
    CategoryRepository categoryRepository;

    @Bean
    public PhotoService photoService() {
        return new PhotoService(photoFactory(), photoRepository);
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryService(categoryRepository, categoryFactory());
    }

    @Bean
    public PhotoFactory photoFactory() {
        return new PhotoFactory();
    }

    @Bean
    public CategoryFactory categoryFactory() {
        return new CategoryFactory();
    }
}
