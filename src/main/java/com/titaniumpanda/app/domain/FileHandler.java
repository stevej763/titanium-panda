package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.PhotoUploadWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

public interface FileHandler<T> {

    Optional<T> uploadFile(MultipartFile file);

    Optional<T> uploadFile(InputStream file, int fileLength);

    Optional<T> uploadFile(PhotoUploadWrapper photo);

}
