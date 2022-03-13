package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.FileUploader;
import com.titaniumpanda.app.domain.IdService;
import com.titaniumpanda.app.domain.PhotoUploadDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class AwsPhotoUploadService implements FileUploader<PhotoUploadDetails> {

    private final IdService idService;
    @Autowired
    private final S3ClientDelegate s3ClientDelegate;

    public AwsPhotoUploadService(IdService idService, S3ClientDelegate s3ClientDelegate) {
        this.idService = idService;
        this.s3ClientDelegate = s3ClientDelegate;
    }

    @Override
    public Optional<PhotoUploadDetails> uploadFile(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            String fileKey = idService.getNewS3UploadId().getId() + ".jpeg";

            return s3ClientDelegate.upload(fileKey, inputStream, file.getSize());
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
