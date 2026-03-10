package com.flashcash.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.engine.internal.Cascade;

import java.util.List;
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    @ManyToMany
    private List<Contact> contacts;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account account;





}
