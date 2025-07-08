package com.writenow.write.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity(name = "likes")
public class Like extends Interaction {
    @ManyToOne
    @JsonBackReference("like-story")
    private Story story;
    @ManyToOne
    @JsonBackReference("like-writer")
    private Writer writer;

    public Like() {
        super();
    }


    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
