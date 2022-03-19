package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.FileHandler;
import com.titaniumpanda.app.domain.IdService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class AwsPhotoFileHandler implements FileHandler<PhotoUploadDetail> {

    private final IdService idService;
    @Autowired
    private final S3ClientDelegate s3ClientDelegate;

    public AwsPhotoFileHandler(IdService idService, S3ClientDelegate s3ClientDelegate) {
        this.idService = idService;
        this.s3ClientDelegate = s3ClientDelegate;
    }

    @Override
    public PhotoUploadDetail uploadFiles(List<PhotoUploadWrapper> photoUploadWrappers, String fileFormat) {
        UUID uploadId = idService.createNewId();
        PhotoUploadBatch batch = new PhotoUploadBatch(uploadId, photoUploadWrappers, fileFormat);
        return s3ClientDelegate.uploadBatch(batch);
    }
}
