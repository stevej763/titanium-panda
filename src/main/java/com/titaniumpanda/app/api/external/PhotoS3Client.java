package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.PhotoUploadDetails;

import java.io.InputStream;
import java.util.Optional;

public class PhotoS3Client {

    private String bucketName;

    public PhotoS3Client(String bucketName) {
        this.bucketName = bucketName;
    }

    public Optional<PhotoUploadDetails> upload(String fileKey, InputStream inputStream) {
        return Optional.empty();
    }
}
