package com.photoarchive.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoarchive.models.Photo;
import com.photoarchive.services.PhotoSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class PhotoControllerTest {
    @MockBean
    private PhotoSearchService photoSearchService;

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
}