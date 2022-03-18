package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.FileHandler;
import com.titaniumpanda.app.domain.IdService;
import com.titaniumpanda.app.domain.PhotoUploadDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class AwsPhotoFileHandler implements FileHandler<PhotoUploadDetails> {

    private final IdService idService;
    @Autowired
    private final S3ClientDelegate s3ClientDelegate;

    public AwsPhotoFileHandler(IdService idService, S3ClientDelegate s3ClientDelegate) {
        this.idService = idService;
        this.s3ClientDelegate = s3ClientDelegate;
    }

    @Override
    public Optional<PhotoUploadDetails> uploadFile(PhotoUploadWrapper photo) {
        return uploadPhoto(photo.getInputStream(), photo.getContentLength());
    }

    @Override
    public Optional<PhotoUploadDetails> uploadFile(InputStream file, int fileLength) {
        return uploadPhoto(file, fileLength);
    }

    @Override
    public Optional<PhotoUploadDetails> uploadFile(MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            return uploadPhoto(inputStream, file.getSize());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<PhotoUploadDetails> uploadPhoto(InputStream inputStream, long fileSize) {
        String fileKey = idService.createNewId() + ".jpeg";
        return s3ClientDelegate.upload(fileKey, inputStream, fileSize);
    }
}
