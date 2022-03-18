package com.titaniumpanda.app.api.external;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class ImageCompressionServiceTest {

    private final ImageCompressionService underTest = new ImageCompressionService();

    @Test
    public void shouldResizeTargetImageTo100Pixels() {
        int targetWidth = 100;

        BufferedImage targetImage = new BufferedImage(1000, 1000, TYPE_INT_RGB);
        BufferedImage result = underTest.compress(targetImage, targetWidth);

        assertThat(result.getWidth(), is(100));
    }

    @Test
    public void shouldMaintainAspectRatioWhenResizing() {
        BufferedImage targetImage = new BufferedImage(800, 600, TYPE_INT_RGB);

        BufferedImage result = underTest.compress(targetImage, 400);

        assertThat(result.getWidth(), Is.is(400));
        assertThat(result.getHeight(), Is.is(300));

    }

}