package com.flashcash.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    private String email;
    private String mdp;
    private String nom;
    private String prenom;
    private String tel;
    private String photo;
}