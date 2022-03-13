package com.titaniumpanda.app.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileUploader<T> {

    Optional<T> uploadFile(MultipartFile file);
}
