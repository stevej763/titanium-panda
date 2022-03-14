package com.titaniumpanda.app;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class S3Configuration {

    Logger LOGGER = LoggerFactory.getLogger(S3Configuration.class);

    @Autowired
    Environment environment;

    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials credentials = getCredentials();

        return AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(getEndpointConfiguration())
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    private BasicAWSCredentials getCredentials() {
        return new BasicAWSCredentials(
                environment.getProperty("aws-access-key"),
                environment.getProperty("aws-secret-key"));
    }

    private AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
        String serviceEndpoint = "http://"+environment.getProperty("s3.minio.hostname")+ ":" + environment.getProperty("s3.minio.port");
        LOGGER.info("Service endpoint={}", serviceEndpoint);
        return new AwsClientBuilder.EndpointConfiguration(
                serviceEndpoint,
                environment.getProperty("aws-region"));
    }

}
