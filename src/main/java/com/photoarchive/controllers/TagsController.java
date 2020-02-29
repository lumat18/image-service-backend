package com.photoarchive.controllers;

import com.photoarchive.models.Tag;
import com.photoarchive.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    @Autowired
    TagRepository tagRepository;

    @GetMapping
    public List<Tag> findAll(){
        return tagRepository.findAll();
    }
}
