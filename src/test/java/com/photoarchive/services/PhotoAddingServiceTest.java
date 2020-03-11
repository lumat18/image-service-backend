package com.photoarchive.services;

import com.photoarchive.models.Photo;
import com.photoarchive.repositories.PhotoRepository;
import com.photoarchive.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoAddingServiceTest {
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private StringValidatorService stringValidatorService;

    @InjectMocks
    private PhotoAddingService photoAddingService;

    @Captor
    ArgumentCaptor<Photo> argCaptor;

    @Test
    public void shouldAddPhotoToDb() {
        Photo photo = new Photo("href", Set.of());
        when(tagRepository.saveAll(photo.getTags())).thenReturn(List.of());
        doNothing().when(tagRepository).flush();
        when(photoRepository.save(photo)).thenReturn(photo);

        photoAddingService.addPhoto(photo);
        verify(photoRepository).save(argCaptor.capture());

        assertEquals(photo, argCaptor.getValue());
        verifyNoMoreInteractions(tagRepository, photoRepository, stringValidatorService);
    }
}