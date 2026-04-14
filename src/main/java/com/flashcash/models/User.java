package com.flashcash.models;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String mdp;
    private String nom;
    private String prenom;
    private String tel;
    private String photo;
    private boolean active;






}
