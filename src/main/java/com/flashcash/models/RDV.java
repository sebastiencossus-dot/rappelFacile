package com.flashcash.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "RDV")
public class RDV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rdv")
    private Integer id;
    private LocalDate dateRdv;
    private Integer isOK;

//    private Prestataire prestataire;
//    private Profession profession;


}