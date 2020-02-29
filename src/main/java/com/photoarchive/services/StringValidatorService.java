package com.photoarchive.services;

import org.springframework.stereotype.Service;

@Service
public class StringValidatorService {
    public String handleTagInput(final String tag){
        //possible future actions on String
        return tag.trim().toLowerCase();
    }
}
