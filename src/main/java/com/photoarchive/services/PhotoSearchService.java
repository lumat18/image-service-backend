package com.photoarchive.services;

import com.photoarchive.models.Photo;
import com.photoarchive.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhotoSearchService {

    PhotoRepository photoRepository;
    private StringValidatorService stringValidatorService;

    public PhotoSearchService(PhotoRepository photoRepository, StringValidatorService stringValidatorService) {
        this.photoRepository = photoRepository;
        this.stringValidatorService = stringValidatorService;
    }

    public List<Photo> getAll() {
        return photoRepository.findAll();
    }

    public Set<Photo> getPhotosByTags(String tagInput) {
        final List<String> tagNames = stringValidatorService.stringInputToTagNames(tagInput);
        final Set<Photo> searchResult = new HashSet<>();

        tagNames.forEach(tagName -> {
            final List<Photo> photoList = getPhotosByTag(tagName);
            searchResult.addAll(photoList);
        });

        return searchResult;
    }

    private List<Photo> getPhotosByTag(String tag) {
        final String tagInput = stringValidatorService.handleTagInput(tag);
        return photoRepository.findAll().stream()
                .filter(photo -> photo.getTags().stream().anyMatch(photoTag -> photoTag.getTag_name().equals(tagInput)))
                .collect(Collectors.toList());
    }
}
