package com.titaniumpanda.app.api.external;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

public class FileConversionServiceTest {

    public final String photoTitle = "photo";

    @Test
    public void shouldConvertMultipartFileToFile() throws IOException {
        FileConversionService underTest = new FileConversionService();

        File photo = new File("photo");
        FileOutputStream fileOutputStream = new FileOutputStream(photo);
        fileOutputStream.write(photoTitle.getBytes());

        MockMultipartFile multipartFile = new MockMultipartFile(photoTitle, photoTitle.getBytes());


        Optional<File> result = underTest.convertUploadedPhotoToFile(multipartFile);

        assertThat(result, Is.is(Optional.of(photo)));
    }
}