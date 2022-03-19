package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.*;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoUploadResourceTest {

    private final File file = mock(File.class);
    private final MockMultipartFile mulipartPhotoFile = new MockMultipartFile("photo", "photo".getBytes());

    private final AwsPhotoFileHandler fileUploadResource = mock(AwsPhotoFileHandler.class);
    private final FileConversionService fileConversionService = mock(FileConversionService.class);
    private final FileUploadPreparationService fileUploadPreparationService = mock(FileUploadPreparationService.class);

    private final PhotoUploadResource underTest = new PhotoUploadResource(fileUploadResource,
                                                            fileConversionService,
                                                            fileUploadPreparationService);

    @Test
    public void shouldReturnCreatedPhotoDtoOnSuccess() throws IOException {
        PhotoUploadWrapper photoUploadWrapper = new PhotoUploadWrapper(mulipartPhotoFile.getInputStream(), "photo".getBytes().length, PhotoResolution.THUMBNAIL);
        PhotoUploadDetail photoUploadDetail = new PhotoUploadDetail(true, UUID.randomUUID(), "fileKey");

        when(fileConversionService.convertUploadedPhotoToFile(mulipartPhotoFile)).thenReturn(Optional.of(file));
        when(file.delete()).thenReturn(true);
        when(fileUploadPreparationService.prepareImage(file, "jpeg", PhotoResolution.THUMBNAIL)).thenReturn(Optional.of(photoUploadWrapper));
        when(fileUploadResource.uploadFiles(List.of(photoUploadWrapper), "jpeg")).thenReturn(photoUploadDetail);

        Optional<PhotoUploadDetail> result = underTest.uploadSet(mulipartPhotoFile);

        assertThat(result, is(Optional.of(photoUploadDetail)));
    }

    @Test
    public void shouldReturnOptionalEmptyIfThereIsAnError() {
        when(fileConversionService.convertUploadedPhotoToFile(mulipartPhotoFile)).thenReturn(Optional.empty());

        Optional<PhotoUploadDetail> result = underTest.uploadSet(mulipartPhotoFile);

        assertThat(result, is(Optional.empty()));
    }

}