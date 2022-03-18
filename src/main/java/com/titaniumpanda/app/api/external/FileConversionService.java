package com.titaniumpanda.app.api.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    public byte[] convertBufferedImageToByteArray(BufferedImage result, String fileFormat) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            convertImageToOutputStream(result, outputStream, fileFormat);
        } catch (IOException exception) {
            LOGGER.error("exception thrown when converting processed image to output stream message={}", exception.getMessage());
        }
        return outputStream.toByteArray();
    }

    private void convertImageToOutputStream(BufferedImage result, ByteArrayOutputStream outputStream, String fileFormat) throws IOException {
        boolean didWrite = ImageIO.write(result, fileFormat, outputStream);
        if (!didWrite) {
            throw new IOException("failed writing buffered image to output stream");
        }
    }
}
