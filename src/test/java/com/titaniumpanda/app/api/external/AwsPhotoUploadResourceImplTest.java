package com.titaniumpanda.app.api.external;

import com.titaniumpanda.app.domain.IdService;
import com.titaniumpanda.app.domain.PhotoUploadDetails;
import com.titaniumpanda.app.domain.ids.S3UploadId;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {"s3.bucketName = test"})
public class AwsPhotoUploadResourceImplTest {


    private final IdService idService = mock(IdService.class);
    private final PhotoS3Client photoS3Client = mock(PhotoS3Client.class);

    private final MockMultipartFile multipartFile = new MockMultipartFile("test", "test".getBytes());
    private final S3UploadId s3UploadId = new S3UploadId();
    private final String fileKey = s3UploadId.getId().toString() + ".jpeg";
    private final PhotoUploadDetails photoUploadDetails = new PhotoUploadDetails(fileKey, fileKey);

    private final AwsPhotoUploadResourceImpl underTest = new AwsPhotoUploadResourceImpl(idService, photoS3Client);

    @Test
    public void shouldReturnPhotoUploadDetailsOnSuccess() throws IOException {
        when(idService.getNewS3UploadId()).thenReturn(s3UploadId);
        when(photoS3Client.upload(eq(fileKey), any(InputStream.class))).thenReturn(Optional.of(photoUploadDetails));

        Optional<PhotoUploadDetails> result = underTest.uploadFile(multipartFile);

        assertThat(result, is(Optional.of(photoUploadDetails)));
    }

}