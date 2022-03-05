package com.example.titaniumpanda.api.photos;

import com.example.titaniumpanda.domain.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api")
public class PhotoResource {

    @Autowired
    private final PhotoService photoService;

    public PhotoResource(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(value = "/photo/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PhotoDto> getPhoto(@PathVariable("id") String id) {
        Optional<PhotoDto> photoDto = photoService.findPhotoBy(id);
        if (photoDto.isPresent()) {
            return ResponseEntity.ok().body(photoDto.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
