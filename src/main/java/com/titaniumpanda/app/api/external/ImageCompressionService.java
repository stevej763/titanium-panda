package com.titaniumpanda.app.api.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCompressionService {

    Logger LOGGER = LoggerFactory.getLogger(ImageCompressionService.class);

    public BufferedImage compress(File imageFile, int targetWidth) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        return compress(bufferedImage, targetWidth);
    }

    public BufferedImage compress(BufferedImage targetImage, int targetWidth) {
        int targetHeight = calculateHeightFromWidth(targetImage.getWidth(), targetImage.getHeight(), targetWidth);
        BufferedImage resizedImage = resizeImage(targetImage, targetWidth, targetHeight);
        LOGGER.info("Image resized targetImageWidth={} targetImageHeight={} resizedImageWidth={} resizedImageHeight={}",
                targetImage.getWidth(), targetImage.getHeight(), resizedImage.getWidth(), resizedImage.getHeight());
        return resizedImage;
    }

    private BufferedImage resizeImage(BufferedImage targetImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(targetImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }

    private int calculateHeightFromWidth(double originalImageWidth, double originalImageHeight, double targetWidth) {
        double aspectRatio = originalImageWidth/originalImageHeight;
        double targetHeightDouble = targetWidth / aspectRatio;
        return (int) targetHeightDouble;
    }
}
