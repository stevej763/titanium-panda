package com.titaniumpanda.app.api.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public byte[] convertBufferedImageToByteArray(BufferedImage bufferedImage, String fileFormat) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, fileFormat, outputStream);
        } catch (IOException exception) {
            LOGGER.error("exception thrown when converting processed image to output stream message={}", exception.getMessage());
        }
        return outputStream.toByteArray();
    }

    public byte[] convertFileToByteArray(File imageFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(imageFile);

            byte[] byteArray = new byte[(int)imageFile.length()];

            fileInputStream.read(byteArray);
            fileInputStream.close();

            return byteArray;
        } catch (IOException exception) {
            LOGGER.error("exception thrown when converting processed image to output stream message={}", exception.getMessage());
            return new byte[0];
        }
    }

}
