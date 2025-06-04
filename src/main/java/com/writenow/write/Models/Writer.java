package com.writenow.write.Models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Locked;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "writers")
public class Writer extends User {

    private Long failCount;
    private Long submissionCount;
    private String url;
    @ManyToMany
    private List<Writer> followers;
    @ManyToMany(mappedBy = "followers")
    private List<Writer> following;
    @OneToMany(mappedBy = "writer")
    @JsonManagedReference("story-writer")
    private List<Story> stories;
    @OneToMany(mappedBy = "writer")
    @JsonManagedReference("like-writer")
    private List<Like> likes;
    @OneToMany(mappedBy = "writer")
    @JsonManagedReference("comment-writer")
    private List<Comment> comments;


    public Writer() {
        super();
        failCount=0L;
        submissionCount=0L;
        followers=new ArrayList<>();
        following=new ArrayList<>();
        stories=new ArrayList<>();
        likes=new ArrayList<>();
        comments=new ArrayList<>();
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public Long getFailCount() {
        return failCount;
    }

    public void setFailCount(Long failCount) {
        this.failCount = failCount;
    }

    public Long getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(Long submissionCount) {
        this.submissionCount = submissionCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Writer> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Writer> followers) {
        this.followers = followers;
    }

    public List<Writer> getFollowing() {
        return following;
    }

    public void setFollowing(List<Writer> following) {
        this.following = following;
    }
}
