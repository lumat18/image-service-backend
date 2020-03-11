package com.photoarchive.services;

import com.photoarchive.models.Photo;
import com.photoarchive.models.Tag;
import com.photoarchive.repositories.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhotoSearchServiceTest {
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private StringValidatorService stringValidatorService;

    @InjectMocks
    private PhotoSearchService photoSearchService;

    @Test
    public void shouldGetAllPhotos(){
        Photo photo = new Photo("href", Set.of());
        when(photoRepository.findAll())
                .thenReturn(List.of(photo));

        final List<Photo> allPhotos = photoSearchService.getAll();

        assertEquals(1, allPhotos.size());
        assertEquals(allPhotos.get(0), photo);
    }

    @Test
    public void shouldGetPhotosByTheirTag(){
        final Tag tag1 = new Tag();
        tag1.setTag_name("tag1");

        final String tagNames = tag1.getTag_name();
        final Photo photo1 = new Photo("href", Set.of(tag1));
        final Photo photo2 = new Photo("href", Set.of(tag1));

        when(stringValidatorService.stringInputToTagNames(tagNames))
                .thenReturn(List.of(tag1.getTag_name()));

        when(stringValidatorService.handleTagInput(tag1.getTag_name()))
                .thenReturn(tag1.getTag_name());

        when(photoRepository.findAll())
                .thenReturn(List.of(photo1, photo2));

        final Set<Photo> photosByTags = photoSearchService.getPhotosByTags(tagNames);

        assertEquals(2, photosByTags.size());
        assertTrue(photosByTags.contains(photo1));
        assertTrue(photosByTags.contains(photo2));
    }

    @Test
    public void shouldReturnEmptyListWhenSearchingForPhotoByNotItsTag(){
        final String tagName = "non-existing-tag";
        final Photo photo = new Photo("href", Set.of());

        when(stringValidatorService.stringInputToTagNames(tagName))
                .thenReturn(List.of(tagName));

        when(stringValidatorService.handleTagInput(tagName))
                .thenReturn(tagName);

        when(photoRepository.findAll())
                .thenReturn(List.of(photo));

        final Set<Photo> photosByTags = photoSearchService.getPhotosByTags(tagName);

        assertEquals(0, photosByTags.size());
        assertFalse(photosByTags.contains(photo));
    }



}