package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.*;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoUploadResourceTest {

    private final String photoThumbnailUrl = "/thisIsWhereTheThumbnailLives";
    private final String photoBaseUrl = "/thisIsWhereTheImageIsLocated";

    private final File file = mock(File.class);
    private final MockMultipartFile mulipartPhotoFile = new MockMultipartFile("photo", "photo".getBytes());

    private final AwsPhotoFileHandler fileUploadResource = mock(AwsPhotoFileHandler.class);
    private final FileConversionService fileConversionService = mock(FileConversionService.class);
    private final FileUploadPreparationService fileUploadPreparationService = mock(FileUploadPreparationService.class);
    private final PhotoUploadResource underTest = new PhotoUploadResource(fileUploadResource,
                                                            fileConversionService,
                                                            fileUploadPreparationService);
    private final PhotoUploadWrapper photoUploadWrapper = mock(PhotoUploadWrapper.class);

    @Test
    public void shouldReturnCreatedPhotoDtoOnSuccess() {
        PhotoUploadDetails photoUploadDetails = new PhotoUploadDetails(photoThumbnailUrl, photoBaseUrl);

        when(fileConversionService.convertUploadedPhotoToFile(mulipartPhotoFile)).thenReturn(Optional.of(file));
        when(file.delete()).thenReturn(true);
        when(fileUploadPreparationService.prepareImage(file)).thenReturn(Optional.of(photoUploadWrapper));
        when(fileUploadResource.uploadFile(photoUploadWrapper)).thenReturn(Optional.of(photoUploadDetails));

        Optional<PhotoUploadDetails> result = underTest.upload(mulipartPhotoFile);

        assertThat(result, is(Optional.of(photoUploadDetails)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfThereIsAnError() {
        when(fileConversionService.convertUploadedPhotoToFile(mulipartPhotoFile)).thenReturn(Optional.empty());

        Optional<PhotoUploadDetails> result = underTest.upload(mulipartPhotoFile);

        assertThat(result, is(Optional.empty()));
    }

}