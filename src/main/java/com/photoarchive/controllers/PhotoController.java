package com.photoarchive.controllers;

import com.photoarchive.models.Photo;
import com.photoarchive.repositories.PhotoRepository;
import com.photoarchive.services.StringValidatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private PhotoRepository photoRepository;
    private StringValidatorService stringValidatorService;

    public PhotoController(PhotoRepository photoRepository, StringValidatorService stringValidatorService) {
        this.photoRepository = photoRepository;
        this.stringValidatorService = stringValidatorService;
    }

    @GetMapping
    public List<Photo> getAllPhotos(){
        return photoRepository.findAll();
    }

    @GetMapping
    public List<Photo> getPhotosByTag(@RequestParam(name = "tag") String tag){
        final String tagInput = stringValidatorService.handleTagInput(tag);
        return photoRepository.findAll().stream()
                .filter(photo -> photo.getTags().contains(tagInput))
                .collect(Collectors.toList());
    }
}
