package com.titaniumpanda.app;

import com.titaniumpanda.app.api.external.AwsPhotoUploadResourceImpl;
import com.titaniumpanda.app.api.external.PhotoS3Client;
import com.titaniumpanda.app.domain.*;
import com.titaniumpanda.app.repository.CategoryRepository;
import com.titaniumpanda.app.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationResourceConfiguration {

    private final IdService idService = new IdService();

    @Autowired
    Environment environment;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Bean
    public PhotoUploadService photoUploadService() {
        return new PhotoUploadService(awsFileUploadResource());
    }

    @Bean
    public AwsPhotoUploadResourceImpl awsFileUploadResource() {
        return new AwsPhotoUploadResourceImpl(idService, photoS3Client());
    }

    @Bean
    public PhotoS3Client photoS3Client() {
        return new PhotoS3Client(environment.getProperty("s3.bucketName"));
    }

    @Bean
    public PhotoService photoSearchService() {
        return new PhotoService(photoFactory(), photoRepository, photoUploadService());
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryService(categoryRepository, categoryFactory());
    }

    @Bean
    public PhotoFactory photoFactory() {
        return new PhotoFactory(idService);
    }

    @Bean
    public CategoryFactory categoryFactory() {
        return new CategoryFactory();
    }
}
