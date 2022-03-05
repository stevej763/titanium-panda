package com.titaniumpanda.app.api.photo;

import com.titaniumpanda.app.domain.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<PhotoDto> getPhoto(@PathVariable("id") String id) {
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
}
