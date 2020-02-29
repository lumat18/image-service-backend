package com.photoarchive.controllers;

import com.photoarchive.models.Photo;
import com.photoarchive.repositories.PhotoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @GetMapping
    public List<Photo> getAllPhotos(){
        return photoRepository.findAll();
    }
}
