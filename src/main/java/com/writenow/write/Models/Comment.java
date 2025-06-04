package com.writenow.write.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity(name = "comments")
public class Comment extends Interaction {


    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String commentText;
    @ManyToOne
    @JsonBackReference("comment-story")
    private Story story;
    @ManyToOne
    @JsonBackReference("comment-writer")
    private Writer writer;


    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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
