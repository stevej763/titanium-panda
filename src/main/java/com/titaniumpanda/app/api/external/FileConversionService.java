package com.titaniumpanda.app.api.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class FileConversionService {

    private final Logger LOGGER = LoggerFactory.getLogger(FileConversionService.class);

    public Optional<File> convertUploadedPhotoToFile(MultipartFile photo) {
        File fileToSaveTo = new File(photo.getName());
        return convert(photo, fileToSaveTo);
    }

    private Optional<File> convert(MultipartFile photo, File fileToSaveTo) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileToSaveTo);
            fileOutputStream.write(photo.getBytes());
            LOGGER.info("converted multipartFile to file");
            return Optional.of(fileToSaveTo);
        } catch (IOException e) {
            LOGGER.error("Error converting multipartFile to file message={}", e.getMessage());
            return Optional.empty();
        }
    }

    //todo implement these method with tests
//    private byte[] convertCompressedImageToByteArray(BufferedImage result) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        boolean didWrite = ImageIO.write(result, "jpeg", outputStream);
//        if (!didWrite) {
//            LOGGER.error("failed writing buffered image to output stream");
//        }
//        return outputStream.toByteArray();
//    }
}
