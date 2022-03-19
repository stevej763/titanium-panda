package com.titaniumpanda.app.api.external;

public enum PhotoResolution {

    THUMBNAIL(600, "thumbnail"),
    SMALL(1280, "small"),
    MEDIUM(1920, "medium"),
//    LARGE(2560, "large"),
//    EXTRA_LARGE(3840, "extra-large"),
    ORIGINAL(0, "original")
    ;

    private final int width;
    private final String folder;

    PhotoResolution(int width, String folder) {
        this.width = width;
        this.folder = folder;
    }

    public int getWidth() {
        return width;
    }

    public String getFolder() {
        return folder;
    }
}
