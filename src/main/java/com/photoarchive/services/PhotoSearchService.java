package com.photoarchive.services;

import com.photoarchive.models.Photo;
import com.photoarchive.repositories.PhotoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoSearchService {

    PhotoRepository photoRepository;
    private StringValidatorService stringValidatorService;

    public PhotoSearchService(PhotoRepository photoRepository, StringValidatorService stringValidatorService) {
        this.photoRepository = photoRepository;
        this.stringValidatorService = stringValidatorService;
    }

    public List<Photo> getAll(){
        return photoRepository.findAll();
    }

    public List<Photo> getPhotosByTag(String tag){
        final String tagInput = stringValidatorService.handleTagInput(tag);
        return photoRepository.findAll().stream()
                .filter(photo -> photo.getTags().stream().anyMatch(photoTag -> photoTag.getTag_name().equals(tagInput)))
                .collect(Collectors.toList());
    }
}
