package com.webapp.models;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private int idUser;

    private String email;
    private String mdp;
    private String nom;
    private String prenom;
    private String tel;
    private String photo;
}