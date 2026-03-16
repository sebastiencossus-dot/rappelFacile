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

        Contact contact = new Contact();

        contact.setUser1(user1);   // utilisateur connecté
        contact.setUser2(user2);   // utilisateur trouvé par email

        contactRepository.save(contact);
    }

    public List<Contact> getContacts(Integer userId){
        return contactRepository.findByUser1Id(userId);
    }

}
