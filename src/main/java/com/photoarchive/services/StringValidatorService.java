package com.photoarchive.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StringValidatorService {
    public String handleTagInput(final String tag) {
        //possible future actions on String
        return tag.trim().toLowerCase();
    }

    public List<String> stringInputToTagNames(String stringInput) {
        final String[] strings = stringInput.split(",");
        for (String s : strings) {
            s = handleTagInput(s);
        }
        return Arrays.asList(strings);
    }
}
