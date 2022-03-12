package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.FileUploadResource;
import com.titaniumpanda.app.domain.IdService;
import com.titaniumpanda.app.domain.PhotoUploadDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class AwsPhotoUploadResourceImpl implements FileUploadResource<PhotoUploadDetails> {

    private IdService idService;
    @Autowired
    private PhotoS3Client photoS3Client;

    public AwsPhotoUploadResourceImpl(IdService idService, PhotoS3Client photoS3Client) {
        this.idService = idService;
        this.photoS3Client = photoS3Client;
    }

    @Override
    public Optional<PhotoUploadDetails> uploadFile(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            String fileKey = idService.getNewS3UploadId().getId() + ".jpeg";

            return photoS3Client.upload(fileKey, inputStream);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
