package com.titaniumpanda.app.domain;

import com.titaniumpanda.app.api.external.PhotoUploadDetail;
import com.titaniumpanda.app.api.external.PhotoUploadWrapper;

import java.util.List;

public interface FileHandler<T> {

    PhotoUploadDetail uploadFiles(List<PhotoUploadWrapper> photoUploadWrappers, String fileFormat);
}
