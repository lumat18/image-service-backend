package com.photoarchive.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tag_id;

    @Column(unique = true)
    private String tag_name;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Photo> photos;

    public Tag() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tag_name.equals(tag.tag_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag_name);
    }
}
