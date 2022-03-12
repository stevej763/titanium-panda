package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.AwsPhotoUploadResourceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhotoUploadServiceTest {

    private final AwsPhotoUploadResourceImpl fileUploadResource = mock(AwsPhotoUploadResourceImpl.class);

    @Test
    public void shouldReturnCreatedPhotoDtoOnSuccess() {
        MockMultipartFile photoFile = new MockMultipartFile("photo", "photo".getBytes());
        String photoThumbnailUrl = "/thisIsWhereTheThumbnailLives";
        String photoBaseUrl = "/thisIsWhereTheImageIsLocated";
        PhotoUploadDetails photoUploadDetails = new PhotoUploadDetails(photoThumbnailUrl, photoBaseUrl);

        PhotoUploadService underTest = new PhotoUploadService(fileUploadResource);


        when(fileUploadResource.uploadFile(photoFile)).thenReturn(Optional.of(photoUploadDetails));

        Optional<PhotoUploadDetails> result = underTest.upload(photoFile);

        assertThat(result, is(Optional.of(photoUploadDetails)));
    }

}