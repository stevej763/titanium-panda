package com.titaniumpanda.app.api.fourzerofour;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

public class ResourceErrorMessage {

    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    private final int statusCode;
    private final String errorMessage;

    public ResourceErrorMessage(@JsonProperty("statusCode") int statusCode,
                                @JsonProperty("errorMessage") String errorMessage) {

        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public static ResourceErrorMessage resourceErrorMessage() {
        return new ResourceErrorMessage(HttpStatus.NOT_FOUND.value(), RESOURCE_NOT_FOUND);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
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
