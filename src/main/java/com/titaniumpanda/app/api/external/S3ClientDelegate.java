package com.titaniumpanda.app.api.external;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


public class S3ClientDelegate {

    Logger LOGGER = LoggerFactory.getLogger(S3ClientDelegate.class);

    private final String bucketName;

    @Autowired
    AmazonS3 s3Client;

    public S3ClientDelegate(String bucketName) {
        this.bucketName = bucketName;
    }

    public PhotoUploadDetail uploadBatch(PhotoUploadBatch batch) {
        List<Boolean> uploadResults = batch.getPhotoUploadWrappers().stream()
                .map(photoUploadWrapper -> uploadSingle(getUri(photoUploadWrapper, batch.getFileKey()),
                        photoUploadWrapper.getInputStream(),
                        photoUploadWrapper.getContentLength()))
                .collect(Collectors.toList());

        if (uploadResults.contains(false)) {
            return new PhotoUploadDetail(false, batch.getUploadId(), batch.getFileFormat());
        } else {
            return new PhotoUploadDetail(true, batch.getUploadId(), batch.getFileFormat());
        }
    }

    private boolean uploadSingle(String uri, InputStream inputStream, long fileSize) {
        ObjectMetadata metadata = constructMetadata(fileSize);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uri, inputStream, metadata);
            s3Client.putObject(putObjectRequest);
            String url = s3Client.getUrl(bucketName, uri).toString();
            LOGGER.info("object uploaded to s3 uri={} size={} bucket={} location={} fullUrl={}",
                    uri, fileSize, bucketName, s3Client.getRegion(), url);
        } catch (Exception e) {
            LOGGER.error("Error putting object into bucket={}", bucketName);
            LOGGER.error(e.getMessage());
            return false;
        }
        return true;
    }

    private ObjectMetadata constructMetadata(long contentLength) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentLength);
        metadata.setContentType("image/jpeg");
        return metadata;
    }

    private String getUri(PhotoUploadWrapper photoUploadWrapper, String fileKey) {
        return photoUploadWrapper.getResolution().getFolder() + "/" + fileKey;
    }
}
