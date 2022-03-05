package com.example.titaniumpanda.api.photos;

import jdk.jfr.ContentType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PhotoResource {

    @GetMapping("/photo")
    public ResponseEntity<PhotoDto> getPhoto() {
        return ResponseEntity.ok().body(new PhotoDto("photo title"));
    }
}
