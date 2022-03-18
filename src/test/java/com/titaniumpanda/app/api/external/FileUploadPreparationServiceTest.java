package com.titaniumpanda.app.api.external;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.mockito.Mockito.mock;

public class FileUploadPreparationServiceTest {

    private final ImageCompressionService imageCompressionService = mock(ImageCompressionService.class);
    private final FileConversionService fileConversionService = mock(FileConversionService.class);

    @Test
    public void shouldReturnPhotoUploadWrapper() {
        File fileToPrepare = new File("src/test/resources/testimage.jpeg");

        FileUploadPreparationService underTest = new FileUploadPreparationService(imageCompressionService, fileConversionService);
    }

}