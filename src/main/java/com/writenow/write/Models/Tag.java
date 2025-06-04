package com.writenow.write.Models;


import jakarta.persistence.*;

import java.util.List;

@Entity(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;
    @ManyToMany(mappedBy = "tags")
    private List<Story> stories;
}
