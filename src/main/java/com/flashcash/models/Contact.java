package com.flashcash.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
public class Contact {

    @Id
    private Integer id;
    @ManyToOne
    User user1;
    @ManyToOne
    User user2;
}
