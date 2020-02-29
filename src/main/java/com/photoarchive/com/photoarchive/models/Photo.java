package com.photoarchive.com.photoarchive.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity(name = "photos")
public class Photo {
    @Id
    private Long photo_id;

    @ManyToMany
            @JoinColumn()
    Set<Tag> tags;
}
