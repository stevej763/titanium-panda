package com.titaniumpanda.app;

import com.titaniumpanda.app.api.external.*;
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
        return new PhotoUploadResource(awsPhotoFileHandler(), fileConversionService(), fileUploadPreparationService());
    }

    @Bean
    public FileUploadPreparationService fileUploadPreparationService() {
        return new FileUploadPreparationService(imageCompressionService(), fileConversionService());
    }

    @Bean
    public FileConversionService fileConversionService() {
        return new FileConversionService();
    }

    @Bean
    public ImageCompressionService imageCompressionService() {
        return new ImageCompressionService();
    }

    @Bean
    public AwsPhotoFileHandler awsPhotoFileHandler() {
        return new AwsPhotoFileHandler(idService, photoS3Client());
    }

    @Bean
    public S3ClientDelegate photoS3Client() {
        return new S3ClientDelegate(environment.getProperty("s3.bucketName"));
    }

    @Bean
    public PhotoService photoService() {
        return new PhotoService(photoFactory(), photoRepository, photoUploadService(), categoryService());
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
