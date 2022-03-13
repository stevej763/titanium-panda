package com.titaniumpanda.app.integration.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.titaniumpanda.app.api.photo.PhotoDto;
import com.titaniumpanda.app.api.photo.PhotoRequestMetadata;
import com.titaniumpanda.app.domain.PhotoService;
import com.titaniumpanda.app.integration.web.AbstractWebTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.fail;

public class S3IntegrationTest extends AbstractWebTest {

    @Autowired
    PhotoService photoService;
    @Autowired
    AmazonS3 s3Client;
    @Autowired
    Environment environment;

    @BeforeAll
    static void beforeAll() {
        collectionName = "photo";
    }

    private final String photoTitle = UUID.randomUUID().toString();
    private final byte[] bytes = photoTitle.getBytes(StandardCharsets.UTF_8);

    @Test
    public void shouldSuccessfullySavePhoto() {
        MockMultipartFile file = new MockMultipartFile(photoTitle, bytes);

        PhotoRequestMetadata metadata = new PhotoRequestMetadata(photoTitle, "S3IntegrationTest", Collections.emptyList());
        photoService.save(file, metadata);

        List<PhotoDto> savedPhotos = photoService.findAll();
        Optional<PhotoDto> savedPhoto = savedPhotos.stream().filter(photoDto -> photoDto.getTitle().equals(photoTitle)).findFirst();

        if (savedPhoto.isEmpty()) {
            fail("Photo failed to save");
        }

        PhotoDto result = savedPhoto.get();

        String photoUrl = result.getPhotoBaseUrl();
        System.out.println(photoUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(photoUrl, String.class);

        assertThat(response.getBody(), is(photoTitle));
    }

}
