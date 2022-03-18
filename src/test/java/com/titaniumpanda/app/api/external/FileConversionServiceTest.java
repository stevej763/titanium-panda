package com.titaniumpanda.app.api.external;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

public class FileConversionServiceTest {

    private final String photoTitle = "src/test/resources/" + UUID.randomUUID();
    private final File photo = new File(photoTitle);
    private final FileConversionService underTest = new FileConversionService();

    @AfterEach
    void tearDown() {
        photo.delete();
    }

    @Test
    public void shouldConvertMultipartFileToFile() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(photoTitle.getBytes());

        MockMultipartFile multipartFile = new MockMultipartFile(photoTitle, photoTitle.getBytes());


        Optional<File> result = underTest.convertUploadedPhotoToFile(multipartFile);

        assertThat(result, Is.is(Optional.of(photo)));
    }

    @Test
    public void shouldConvertImageToByteArray() throws IOException {
        BufferedImage bufferedImage = new BufferedImage(100, 100, 1);

        byte[] result = underTest.convertBufferedImageToByteArray(bufferedImage, "jpeg");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpeg", stream);
        final byte[] expected = stream.toByteArray();

        assertThat(result, Is.is(expected));
    }
}