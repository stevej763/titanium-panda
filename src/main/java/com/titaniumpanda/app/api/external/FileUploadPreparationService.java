package com.titaniumpanda.app.api.external;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Optional;

public class FileUploadPreparationService {

    @Autowired
    private final ImageCompressionService imageCompressionService;
    @Autowired
    private final FileConversionService fileConversionService;

    public FileUploadPreparationService(ImageCompressionService imageCompressionService, FileConversionService fileConversionService) {
        this.imageCompressionService = imageCompressionService;
        this.fileConversionService = fileConversionService;
    }

    public Optional<PhotoUploadWrapper> prepareImage(File imageFile) {
        return Optional.empty();
    }

    //todo implement with tests
//    private PhotoUploadWrapper prepareImage(File imageFile) throws IOException {
//        BufferedImage result = imageCompressionService.compress(imageFile, 1920);
//
//        byte[] imageByteArray = fileConversionService.convertBufferedImageToByteArray(result);
//        InputStream inputStream = new ByteArrayInputStream(imageByteArray);
//
//        return new PhotoUploadWrapper(inputStream, imageByteArray.length);
//    }
}
