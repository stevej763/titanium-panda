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


public class PhotoS3Client {

    Logger LOGGER = LoggerFactory.getLogger(PhotoS3Client.class);

    private final String bucketName;

    @Autowired
    AmazonS3 s3Client;

    public PhotoS3Client(String bucketName) {
        this.bucketName = bucketName;
    }

    public Optional<PhotoUploadDetails> upload(String fileKey, InputStream inputStream, long fileSize) {
        ObjectMetadata metadata = getMetadata(fileSize);
        try {
            LOGGER.info("Attempting to put object with fileKey={} of size={} into bucket={} at location={}",
                    fileKey, fileSize, bucketName, s3Client.getRegion());
            s3Client.putObject(new PutObjectRequest(bucketName, fileKey, inputStream, metadata));
        } catch (Exception e) {
            LOGGER.error("Error putting object..");
            LOGGER.error(e.getMessage());
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
