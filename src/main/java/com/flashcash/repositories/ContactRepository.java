package com.flashcash.repositories;

import com.flashcash.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findByUser1Id(Integer userId);
}
