package com.titaniumpanda.app;

import com.titaniumpanda.app.api.external.AwsPhotoUploadService;
import com.titaniumpanda.app.api.external.S3ClientDelegate;
import com.titaniumpanda.app.api.external.PhotoUploadResource;
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
    public PhotoUploadResource photoUploadService() {
        return new PhotoUploadResource(awsFileUploadResource());
    }

    @Bean
    public AwsPhotoUploadService awsFileUploadResource() {
        return new AwsPhotoUploadService(idService, photoS3Client());
    }

    @Bean
    public S3ClientDelegate photoS3Client() {
        return new S3ClientDelegate(environment.getProperty("s3.bucketName"));
    }

    @Bean
    public PhotoService photoService() {
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
        return new CategoryFactory(idService);
    }
}
