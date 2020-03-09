package com.photoarchive.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoarchive.models.Photo;
import com.photoarchive.services.PhotoAddingService;
import com.photoarchive.services.PhotoSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class PhotoControllerTest {
    @MockBean
    private PhotoSearchService photoSearchService;

    @Mock
    private PhotoAddingService photoAddingService;

    @InjectMocks
    private PhotoController photoController;

    @Autowired
    private MockMvc mockMvc;

    private List<Photo> samplePhotoList;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        Photo samplePhoto = new Photo(99L, "sample", Set.of());
        samplePhotoList = new ArrayList<>();
        objectMapper = new ObjectMapper();
        samplePhotoList.add(samplePhoto);
    }

    @Test
    public void shouldReturnObjectJsonNull() throws Exception {
        //given
        when(photoSearchService.getAll()).thenReturn(List.of());

        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/photos/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(List.of())));
        verify(photoSearchService).getAll();
    }

    @Test
    public void shouldReturnObjectJson() throws Exception {
        //given
        when(photoSearchService.getAll()).thenReturn(samplePhotoList);

        //when+then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/photos/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(samplePhotoList)));
        verify(photoSearchService).getAll();
    }

    @Test
    public void shouldGetAllPhotos() {
        //given
        when(photoSearchService.getAll()).thenReturn(samplePhotoList);
        //when
        final List<Photo> allPhotos = photoController.getAllPhotos();
        //then
        assertThat(allPhotos.size()).isEqualTo(1);
        verify(photoSearchService).getAll();
    }

    @Test
    public void shouldGetPhotosByTags() {
        //given
        Set photoSet = new HashSet(samplePhotoList);
        when(photoSearchService.getPhotosByTags("tags")).thenReturn(photoSet);
        //when
        final Set<Photo> photosByTags = photoController.getPhotosByTags("tags");
        //then
        assertThat(photosByTags.containsAll(samplePhotoList));
        verify(photoSearchService).getPhotosByTags("tags");
    }

}