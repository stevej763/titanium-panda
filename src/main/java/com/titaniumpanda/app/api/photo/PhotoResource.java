package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.domain.Photo;
import com.titaniumpanda.app.domain.PhotoService;
import com.titaniumpanda.app.domain.ids.PhotoId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/photo")
public class PhotoResource {

    @Autowired
    private final PhotoService photoService;

    public PhotoResource(PhotoService photoService) {
        this.photoService = photoService;
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

    @PostMapping(value = "{id}")
    public ResponseEntity<Photo> uploadPhoto(@PathVariable("id") String id) {
        Photo testPhoto = new Photo(new PhotoId(id), "does this work", "url", "description", LocalDateTime.now(), LocalDateTime.now(), "baseUrl", Collections.emptyList());
        Photo result = photoService.save(testPhoto);
        return ResponseEntity.ok().body(result);
    }
}
