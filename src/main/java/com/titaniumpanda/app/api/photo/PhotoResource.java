package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.domain.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.titaniumpanda.app.api.photo.PhotoResource.PHOTO_RESOURCE_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(PHOTO_RESOURCE_URL)
public class PhotoResource {

    public static final String PHOTO_RESOURCE_URL = "api/photo";

    @Autowired
    private final PhotoService photoService;

    public PhotoResource(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(value = "{photoId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> getPhoto(@PathVariable("photoId") UUID photoId) {
        Optional<PhotoDto> photoDto = photoService.findPhotoBy(photoId);
        if (photoDto.isPresent()) {
            return ResponseEntity.ok().body(photoDto.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/delete/{photoId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("photoId") UUID photoId) {
        boolean result = photoService.deletePhoto(photoId);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhotoDto>> getAllPhotos() {
        List<PhotoDto> photos = photoService.findAll();
        return ResponseEntity.ok().body(photos);
    }

    @GetMapping(value = "/category/{categoryId}")
    public ResponseEntity<List<PhotoDto>> getPhotosForCategory(@PathVariable("categoryId") UUID categoryId) {
        List<PhotoDto> photosForCategory = photoService.findByCategoryId(categoryId);
        return ResponseEntity.ok().body(photosForCategory);
    }

    @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<PhotoDto> addPhoto(PhotoRequestMetadata photoRequestMetadata, MultipartFile photo) {
        Optional<PhotoDto> response = photoService.save(photo, photoRequestMetadata);
        if (response.isPresent()) {
            PhotoDto photoDto = response.get();
            String resourceLocation = PHOTO_RESOURCE_URL + "/" + photoDto.getPhotoId();
            return ResponseEntity.created(URI.create(resourceLocation)).body(photoDto);
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

}
