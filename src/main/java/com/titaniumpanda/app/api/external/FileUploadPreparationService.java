package com.titaniumpanda.app.api.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

public class FileUploadPreparationService {

    Logger LOGGER = LoggerFactory.getLogger(FileUploadPreparationService.class);

    @Autowired
    private final ImageCompressionService imageCompressionService;
    @Autowired
    private final FileConversionService fileConversionService;

    public FileUploadPreparationService(ImageCompressionService imageCompressionService, FileConversionService fileConversionService) {
        this.imageCompressionService = imageCompressionService;
        this.fileConversionService = fileConversionService;
    }

    public Optional<PhotoUploadWrapper> prepareImage(File imageFile, String fileFormat, PhotoResolution resolution) {
        try {
            if(resolution == PhotoResolution.ORIGINAL) {
                PhotoUploadWrapper result = createWrapperForOriginalFile(imageFile, resolution);
                return Optional.of(result);
            } else {
                BufferedImage result = imageCompressionService.compress(imageFile, resolution.getWidth());
                PhotoUploadWrapper photoUploadWrapper = createPhotoUploadWrapper(result, fileFormat, resolution);
                return Optional.of(photoUploadWrapper);
            }
        } catch (CompressionException e) {
            LOGGER.error("Error preparing file for upload message={}",e.getMessage());
            return Optional.empty();
        }
    }

    private PhotoUploadWrapper createWrapperForOriginalFile(File imageFile, PhotoResolution resolution) {
        byte[] imageByteArray = fileConversionService.convertFileToByteArray(imageFile);
        InputStream inputStream = new ByteArrayInputStream(imageByteArray);
        return new PhotoUploadWrapper(inputStream, imageByteArray.length, resolution);
    }

    private PhotoUploadWrapper createPhotoUploadWrapper(BufferedImage result, String fileFormat, PhotoResolution resolution) {
        byte[] imageByteArray = fileConversionService.convertBufferedImageToByteArray(result, fileFormat);
        InputStream inputStream = new ByteArrayInputStream(imageByteArray);
        return new PhotoUploadWrapper(inputStream, imageByteArray.length, resolution);
    }

}
