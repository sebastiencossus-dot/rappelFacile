package com.flashcash.services;


import com.flashcash.models.Contact;
import com.flashcash.models.User;
import com.flashcash.repositories.ContactRepository;
import com.flashcash.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;

    public void addContact(String email, Integer currentUserId) {

        User user1 = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user2 = userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with this email not found"));

        if (user1.getId().equals(user2.getId())) {
            throw new RuntimeException("Vous ne pouvez pas vous ajouter vous-même");
        }

        boolean exists = contactRepository.existsByUser1IdAndUser2Id(user1.getId(), user2.getId());

        if (exists) {
            throw new RuntimeException("Ce contact existe déjà");
        }

        Contact contact = new Contact();

        contact.setUser1(user1);   // utilisateur connecté
        contact.setUser2(user2);   // utilisateur trouvé par email

        contactRepository.save(contact);
    }

    public List<Contact> getContacts(Integer userId){
        return contactRepository.findByUser1IdOrderByUser2EmailAsc(userId);
    }

}
