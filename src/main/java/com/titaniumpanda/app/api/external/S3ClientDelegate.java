package com.titaniumpanda.app.api.external;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.titaniumpanda.app.domain.PhotoUploadDetails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.Optional;


public class S3ClientDelegate {

    Logger LOGGER = LoggerFactory.getLogger(S3ClientDelegate.class);

    private final String bucketName;

    @Autowired
    AmazonS3 s3Client;

    public S3ClientDelegate(String bucketName) {
        this.bucketName = bucketName;
    }

    public Optional<PhotoUploadDetails> upload(String fileKey, InputStream inputStream, long fileSize) {
        ObjectMetadata metadata = getMetadata(fileSize);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileKey, inputStream, metadata);
            s3Client.putObject(putObjectRequest);
            LOGGER.info("object uploaded to s3 fileKey={} size={} bucket={} location={}",
                    fileKey, fileSize, bucketName, s3Client.getRegion());
        } catch (Exception e) {
            LOGGER.error("Error putting object into bucket={}", bucketName);
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
        String url = s3Client.getUrl(bucketName, fileKey).toString();
        return Optional.of(new PhotoUploadDetails(url, url));
    }

    private ObjectMetadata getMetadata(long contentLength) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType("image/jpeg");
        return metadata;
    }
}
