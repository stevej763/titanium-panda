package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.IdService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AwsPhotoFileHandlerTest {


    private final IdService idService = mock(IdService.class);
    private final S3ClientDelegate s3ClientDelegate = mock(S3ClientDelegate.class);
    private final byte[] bytes = "bytes".getBytes(StandardCharsets.UTF_8);
    private final MockMultipartFile multipartFile = new MockMultipartFile("bytes", bytes);
    private final UUID s3UploadId = UUID.randomUUID();
    private final String fileKey = s3UploadId + ".jpeg";
    private final PhotoUploadDetail photoUploadDetail = new PhotoUploadDetail(true, UUID.randomUUID(), fileKey);

    private final AwsPhotoFileHandler underTest = new AwsPhotoFileHandler(idService, s3ClientDelegate);

    @Test
    public void shouldReturnPhotoUploadDetailOnSuccess() throws IOException {
        when(idService.createNewId()).thenReturn(s3UploadId);
        when(s3ClientDelegate.uploadBatch(any(PhotoUploadBatch.class))).thenReturn(photoUploadDetail);

        PhotoUploadDetail result = underTest.uploadFiles(List.of(new PhotoUploadWrapper(multipartFile.getInputStream(), bytes.length, PhotoResolution.THUMBNAIL)), ".jpeg");

        assertThat(result, is(photoUploadDetail));
    }

}