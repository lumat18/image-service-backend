package com.photoarchive.controllers;

import com.photoarchive.models.Photo;
import com.photoarchive.repositories.PhotoRepository;
import com.photoarchive.services.StringValidatorService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public List<Photo> getAllPhotos(){
        return photoRepository.findAll();
    }

    @GetMapping
    public List<Photo> getPhotosByTag(@RequestParam(name = "tag") String tag){
        final String tagInput = stringValidatorService.handleTagInput(tag);
        return photoRepository.findAll().stream()
                .filter(photo -> photo.getTags().stream().anyMatch(photoTag -> photoTag.getTag_name().equals(tagInput)))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addPhoto(@RequestBody Photo photo){
        photo.getTags().forEach(tag -> tag.setTag_name(stringValidatorService.handleTagInput(tag.getTag_name())));
        photoRepository.save(photo);
    }
}