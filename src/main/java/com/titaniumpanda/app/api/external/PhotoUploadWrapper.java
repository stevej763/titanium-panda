package com.titaniumpanda.app.api.external;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.InputStream;

public class PhotoUploadWrapper {

    private final InputStream inputStream;
    private final int contentLength;
    private final PhotoResolution resolution;

    public PhotoUploadWrapper(InputStream inputStream, int contentLength, PhotoResolution resolution) {
        this.inputStream = inputStream;
        this.contentLength = contentLength;
        this.resolution = resolution;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public int getContentLength() {
        return contentLength;
    }

    public PhotoResolution getResolution() {
        return resolution;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
