package com.photoarchive.controllers;

import com.photoarchive.services.PhotoAddingService;
import com.photoarchive.services.PhotoSearchService;
import com.photoarchive.models.Photo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {


    private PhotoSearchService photoSearchService;
    private PhotoAddingService photoAddingService;


    public PhotoController(PhotoSearchService photoSearchService, PhotoAddingService photoAddingService) {
        this.photoSearchService = photoSearchService;
        this.photoAddingService = photoAddingService;
    }

    @GetMapping("/all")
    public List<Photo> getAllPhotos(){
        return photoSearchService.getAll();
    }

    @GetMapping
    public List<Photo> getPhotosByTags(@RequestParam(name = "tags") String tags){
        return photoSearchService.getPhotosByTags(tags);
    }

    @PostMapping
    public void addPhoto(@RequestBody Photo photo){
        photoAddingService.addPhoto(photo);
    }
}