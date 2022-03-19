package com.titaniumpanda.app.api.external;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import static com.titaniumpanda.app.api.external.PhotoResolution.ORIGINAL;
import static com.titaniumpanda.app.api.external.PhotoResolution.THUMBNAIL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class FileUploadPreparationServiceTest {

    private final ImageCompressionService imageCompressionService = mock(ImageCompressionService.class);
    private final FileConversionService fileConversionService = mock(FileConversionService.class);
    private final BufferedImage bufferedImage = mock(BufferedImage.class);

    @Test
    public void shouldReturnPhotoUploadWrapperForNonOriginalImage() throws CompressionException {
        File fileToPrepare = new File("src/test/resources/testimage.jpeg");
        byte[] bytes = "test".getBytes();

        FileUploadPreparationService underTest = new FileUploadPreparationService(imageCompressionService, fileConversionService);

        when(imageCompressionService.compress(fileToPrepare, THUMBNAIL.getWidth())).thenReturn(bufferedImage);
        when(fileConversionService.convertBufferedImageToByteArray(bufferedImage, "jpeg")).thenReturn(bytes);

        Optional<PhotoUploadWrapper> result = underTest.prepareImage(fileToPrepare, "jpeg", THUMBNAIL);

        assertThat(result.get().getContentLength(), is(bytes.length));
    }

    @Test
    public void shouldReturnPhotoUploadWrapperForOriginalImage() throws CompressionException {
        File fileToPrepare = new File("src/test/resources/testimage.jpeg");
        byte[] bytes = "test".getBytes();

        FileUploadPreparationService underTest = new FileUploadPreparationService(imageCompressionService, fileConversionService);

        when(fileConversionService.convertFileToByteArray(fileToPrepare)).thenReturn(bytes);

        Optional<PhotoUploadWrapper> result = underTest.prepareImage(fileToPrepare, "jpeg", ORIGINAL);

        verify(imageCompressionService, never()).compress(fileToPrepare, ORIGINAL.getWidth());
        assertThat(result.get().getContentLength(), is(bytes.length));
    }

}