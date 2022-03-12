package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.domain.PhotoService;
import com.titaniumpanda.app.domain.PhotoUploadService;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.titaniumpanda.app.api.photo.PhotoResource.PHOTO_RESOURCE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(PHOTO_RESOURCE_URL)
public class PhotoResource {

    public static final String PHOTO_RESOURCE_URL = "api/photo";

    @Autowired
    private final PhotoService photoService;
    @Autowired
    private final PhotoUploadService photoUploadService;

    public PhotoResource(PhotoService photoService, PhotoUploadService photoUploadService) {
        this.photoService = photoService;
        this.photoUploadService = photoUploadService;
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> getPhoto(@PathVariable("id") PhotoId id) {
        Optional<PhotoDto> photoDto = photoService.findPhotoBy(id);
        if (photoDto.isPresent()) {
            return ResponseEntity.ok().body(photoDto.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhotoDto>> getAllPhotos() {
        List<PhotoDto> photos = photoService.findAll();
        return ResponseEntity.ok().body(photos);
    }

    @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<PhotoDto> addPhoto(PhotoRequestMetadata photoRequestMetadata, MultipartFile photo) {
        Optional<PhotoDto> response = photoService.save(photo, photoRequestMetadata);
        if (response.isPresent()) {
            PhotoDto photoUploadDetails = response.get();
            String resourceLocation = PHOTO_RESOURCE_URL + "/" + photoUploadDetails.getPhotoIdAsString();
            return ResponseEntity.created(URI.create(resourceLocation)).body(photoUploadDetails);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

}
