package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PhotoUploadResource {

    private static final String JPEG_SUFFIX = "jpeg";

    Logger LOGGER = LoggerFactory.getLogger(PhotoUploadResource.class);

    @Autowired
    private final FileHandler<PhotoUploadDetail> fileHandler;
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

    public Optional<PhotoUploadDetail> uploadSet(MultipartFile photo) {
        Optional<File> convertedFile = fileConversionService.convertUploadedPhotoToFile(photo);
        if (convertedFile.isPresent()) {
            File imageFile = convertedFile.get();
            Optional<PhotoUploadDetail> photoUploadDetail = uploadFileForEachResolution(imageFile);
            cleanup(imageFile);
            return photoUploadDetail;
        }
        return Optional.empty();
    }

    private Optional<PhotoUploadDetail> uploadFileForEachResolution(File imageFile) {
        List<PhotoUploadWrapper> photoUploadWrapperList = Arrays.stream(PhotoResolution.values())
                .map(resolution ->  fileUploadPreparationService.prepareImage(imageFile, JPEG_SUFFIX, resolution))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        PhotoUploadDetail photoUploadDetail = fileHandler.uploadFiles(photoUploadWrapperList, JPEG_SUFFIX);
        if (photoUploadDetail.isSuccess()) {
            return Optional.of(photoUploadDetail);
        } else {
            LOGGER.error("one or more files failed to upload, uploadId={}", photoUploadDetail.getUploadId());
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
