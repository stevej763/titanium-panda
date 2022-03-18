package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.FileHandler;
import com.titaniumpanda.app.domain.PhotoUploadDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

public class PhotoUploadResource {

    Logger LOGGER = LoggerFactory.getLogger(PhotoUploadResource.class);

    @Autowired
    private final FileHandler<PhotoUploadDetails> fileHandler;
    @Autowired
    private final FileConversionService fileConversionService;
    @Autowired
    private final FileUploadPreparationService fileUploadPreparationService;

    public PhotoUploadResource(AwsPhotoFileHandler fileHandler,
                               FileConversionService fileConversionService,
                               FileUploadPreparationService fileUploadPreparationService) {
        this.fileHandler = fileHandler;
        this.fileConversionService = fileConversionService;
        this.fileUploadPreparationService = fileUploadPreparationService;
    }

    public Optional<PhotoUploadDetails> upload(MultipartFile photo) {
        Optional<File> convertedFile = fileConversionService.convertUploadedPhotoToFile(photo);
        if (convertedFile.isPresent()) {
            File imageFile = convertedFile.get();
            Optional<PhotoUploadDetails> photoUploadDetails = uploadFile(imageFile);
            cleanup(imageFile);
            return photoUploadDetails;
        }
        return Optional.empty();
    }

    private Optional<PhotoUploadDetails> uploadFile(File imageFile) {
        Optional<PhotoUploadWrapper> optionalPhotoUploadWrapper = fileUploadPreparationService.prepareImage(imageFile);
        if(optionalPhotoUploadWrapper.isPresent()) {
            PhotoUploadWrapper photoUploadWrapper = optionalPhotoUploadWrapper.get();
            return fileHandler.uploadFile(photoUploadWrapper);
        } else {
            LOGGER.error("No photo upload wrapper returned from fileUploadPreparationService");
            return Optional.empty();
        }
    }

    private void cleanup(File imageFile) {
        boolean isDeleted = imageFile.delete();
        if (isDeleted) {
            LOGGER.info("temporary local image deleted successfully");
        } else {
            LOGGER.error("error deleting temporary local image");
        }
    }
}
