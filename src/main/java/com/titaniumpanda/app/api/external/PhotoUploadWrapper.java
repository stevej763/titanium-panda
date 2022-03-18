package com.titaniumpanda.app.api.external;

import java.io.InputStream;

public class PhotoUploadWrapper {

    private final InputStream inputStream;
    private final int contentLength;

    public PhotoUploadWrapper(InputStream inputStream, int contentLength) {

        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public int getContentLength() {
        return contentLength;
    }
}
