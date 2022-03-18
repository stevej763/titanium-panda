package com.titaniumpanda.app.api.external;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileUploadPreparationServiceTest {

    private final ImageCompressionService imageCompressionService = mock(ImageCompressionService.class);
    private final FileConversionService fileConversionService = mock(FileConversionService.class);
    private final BufferedImage bufferedImage = mock(BufferedImage.class);

    @Test
    public void shouldReturnPhotoUploadWrapper() throws CompressionException {
        File fileToPrepare = new File("src/test/resources/testimage.jpeg");

        FileUploadPreparationService underTest = new FileUploadPreparationService(imageCompressionService, fileConversionService);

        when(imageCompressionService.compress(fileToPrepare, 1920)).thenReturn(bufferedImage);
        byte[] bytes = "test".getBytes();
        when(fileConversionService.convertBufferedImageToByteArray(bufferedImage, "jpeg")).thenReturn(bytes);
        Optional<PhotoUploadWrapper> result = underTest.prepareImage(fileToPrepare, "jpeg");

        assertThat(result.get().getContentLength(), is(bytes.length));
    }

}