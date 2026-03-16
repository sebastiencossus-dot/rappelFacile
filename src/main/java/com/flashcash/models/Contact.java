package com.flashcash.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user1_id")
    User user1;
    @ManyToOne
    @JoinColumn(name = "user2_id")
    User user2;
}
