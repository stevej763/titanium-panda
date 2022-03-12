package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.AwsPhotoUploadResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class PhotoUploadService {

    @Autowired
    private final AwsPhotoUploadResourceImpl fileUploadResource;

    public PhotoUploadService(AwsPhotoUploadResourceImpl fileUploadResource) {
        this.fileUploadResource = fileUploadResource;
    }

    public Optional<PhotoUploadDetails> upload(MultipartFile photo) {
        return fileUploadResource.uploadFile(photo);
    }
}
