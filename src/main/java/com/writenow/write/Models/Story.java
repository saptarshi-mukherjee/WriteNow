package com.writenow.write.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String title;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String body;
    private LocalDateTime submissionDate;
    @ManyToOne
    @JsonBackReference("story-writer")
    private Writer writer;
    @ManyToOne
    @JsonBackReference("story-prompt")
    private Prompt prompt;
    private Double score;
    private ComplianceStatus complianceStatus;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags;
    private StoryStatus storyStatus;
    @OneToMany(mappedBy = "story")
    @JsonManagedReference("like-story")
    private List<Like> likes;
    @OneToMany(mappedBy = "story")
    @JsonManagedReference("comment-story")
    private List<Comment> comments;


    public Story() {
        submissionDate= LocalDateTime.now();
        score=0.0;
        complianceStatus=ComplianceStatus.PENDING;
        tags=new ArrayList<>();
        storyStatus=StoryStatus.PARTICIPANT;
        likes=new ArrayList<>();
        comments=new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public ComplianceStatus getComplianceStatus() {
        return complianceStatus;
    }

    public void setComplianceStatus(ComplianceStatus complianceStatus) {
        this.complianceStatus = complianceStatus;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public StoryStatus getStoryStatus() {
        return storyStatus;
    }

    public void setStoryStatus(StoryStatus storyStatus) {
        this.storyStatus = storyStatus;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
