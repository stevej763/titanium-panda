package com.titaniumpanda.app.api.external;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

public class PhotoUploadDetail {
    private final boolean success;
    private final UUID key;
    private final String fileKey;

    public PhotoUploadDetail(boolean success, UUID uploadId, String fileKey) {
        this.success = success;
        this.key = uploadId;
        this.fileKey = fileKey;
    }

    public boolean isSuccess() {
        return success;
    }

    public UUID getUploadId() {
        return key;
    }

    public String getFileKey() {
        return fileKey;
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
