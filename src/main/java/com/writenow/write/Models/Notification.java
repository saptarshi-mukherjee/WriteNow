package com.writenow.write.Models;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Writer publisher;
    @ManyToOne
    private Interaction interaction;
    @ManyToOne
    private Writer subscriber;
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

    public Writer getPublisher() {
        return publisher;
    }

    public void setPublisher(Writer publisher) {
        this.publisher = publisher;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public Writer getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Writer subscriber) {
        this.subscriber = subscriber;
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
