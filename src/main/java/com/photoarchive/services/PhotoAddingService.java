package com.photoarchive.services;

import com.photoarchive.models.Photo;
import com.photoarchive.models.Tag;
import com.photoarchive.repositories.PhotoRepository;
import com.photoarchive.repositories.TagRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PhotoAddingService {

    PhotoRepository photoRepository;
    TagRepository tagRepository;
    StringValidatorService stringValidatorService;

    public PhotoAddingService(PhotoRepository photoRepository, TagRepository tagRepository, StringValidatorService stringValidatorService) {
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
        this.stringValidatorService = stringValidatorService;
    }

    @Transactional
    public void addPhoto(Photo photo){
        Set<Tag> tagsToAdd = new HashSet<>();
        photo.getTags().forEach(tag -> tag.setTag_name(stringValidatorService.handleTagInput(tag.getTag_name())));
        photo.getTags().forEach(tag -> {
            Optional<Tag> one = tagRepository.findOne(Example.of(tag));
            if (one.isPresent()){
                tagsToAdd.add(one.get());
            }else {
                tagsToAdd.add(tag);
            }
        });
        System.out.println("we are here");
        photo.setTags(tagsToAdd);
        tagRepository.saveAll(photo.getTags());
        tagRepository.flush();
        photoRepository.save(photo);
    }
}
