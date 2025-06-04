package com.writenow.write.Models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "contests")
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime expiry;
    @ManyToOne
    @JsonBackReference("contest-moderator")
    private Moderator createdBy;
    @OneToMany(mappedBy = "contest", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonManagedReference("prompt-contest")
    private List<Prompt> prompts;
    private ContestStatus contestStatus;


    public Contest() {
        createdAt=LocalDateTime.now();
        expiry=createdAt.plusDays(7);
        prompts=new ArrayList<>();
        contestStatus=ContestStatus.NOT_EXPIRED;
    }


    public ContestStatus getContestStatus() {
        return contestStatus;
    }

    public void setContestStatus(ContestStatus contestStatus) {
        this.contestStatus = contestStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    public Moderator getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Moderator createdBy) {
        this.createdBy = createdBy;
    }

    public List<Prompt> getPrompts() {
        return prompts;
    }

    public void setPrompts(List<Prompt> prompts) {
        this.prompts = prompts;
    }
}
