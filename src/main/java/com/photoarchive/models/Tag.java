package com.photoarchive.com.photoarchive.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tag_id;
    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Photo> photos;

    public Tag() {
    }
}
