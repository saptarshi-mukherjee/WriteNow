package com.writenow.write.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "interactions")
@Inheritance(strategy = InheritanceType.JOINED)
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;


    public Interaction() {
        createdAt=LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
