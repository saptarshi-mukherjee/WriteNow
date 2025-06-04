package com.writenow.write.Models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Writer writer;
    @OneToOne
    private Interaction interaction;
    @ManyToMany
    private List<Writer> subscribers;
    private LocalDateTime createdAt;
    private NotificationType type;


    public Notification() {
        createdAt=LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public List<Writer> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Writer> subscribers) {
        this.subscribers = subscribers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
}
