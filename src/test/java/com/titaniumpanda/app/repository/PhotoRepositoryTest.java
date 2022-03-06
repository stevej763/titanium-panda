package com.titaniumpanda.app.repository;

import com.titaniumpanda.app.domain.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class PhotoRepositoryTest extends AbstractMongoRepositoryTest {

    @Autowired
    PhotoRepository photoRepository;

    @Test
    public void shouldFindListOfPhotos() {
        Photo photo1 = new Photo("title", UUID.randomUUID().toString(), "thumbnail", "photodescription");
        Photo photo2 = new Photo("title", UUID.randomUUID().toString(), "thumbnail", "photodescription");
        Photo photo3 = new Photo("title", UUID.randomUUID().toString(), "thumbnail", "photodescription");

        mongoTemplate.save(photo1);
        mongoTemplate.save(photo2);
        mongoTemplate.save(photo3);

        List<Photo> result = photoRepository.findAll();
        assertThat(result, hasItem(photo1));
        assertThat(result, hasItem(photo2));
        assertThat(result, hasItem(photo3));
    }

}