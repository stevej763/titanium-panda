package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.FileUploader;
import com.titaniumpanda.app.domain.PhotoUploadDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class PhotoUploadResource {

    @Autowired
    private final FileUploader<PhotoUploadDetails> photoUploadService;

    public PhotoUploadResource(AwsPhotoUploadService photoUploadService) {
        this.photoUploadService = photoUploadService;
    }

    public Optional<PhotoUploadDetails> upload(MultipartFile photo) {
        return photoUploadService.uploadFile(photo);
    }
}
