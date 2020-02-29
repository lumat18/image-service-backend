package com.photoarchive;

import com.photoarchive.models.Photo;
import com.photoarchive.models.Tag;
import com.photoarchive.repositories.PhotoRepository;
import com.photoarchive.repositories.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PhotoRepository photoRepository, TagRepository tagRepository){
        return args -> {
            final Tag tag = new Tag();
            tag.setTag_name("meme");
            final Tag tag1 = new Tag();
            tag1.setTag_name("java");
            final Photo photo = new Photo("https://devhumor.com/content/uploads/images/May2018/sun_java.jpg", Set.of(tag, tag1));

            log.info("Preloading... " + tagRepository.save(tag));
            log.info("Preloading... " + tagRepository.save(tag1));
            log.info("Preloading... " + photoRepository.save(photo));
        };
    }
}
