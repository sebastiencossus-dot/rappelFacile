package com.flashcash.controllers;

import com.flashcash.models.Contact;
import com.flashcash.models.User;
import com.flashcash.services.ContactService;
import com.flashcash.services.SessionService;

import com.flashcash.services.TransferService;
import com.flashcash.services.UserService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class ContactController {

    private final SessionService sessionService;
    private final ContactService contactService;
    private final TransferService transferService;

    public ContactController(SessionService sessionService,
                             ContactService contactService, TransferService transferService, UserService userService) {

        this.sessionService = sessionService;
        this.contactService = contactService;
        this.transferService = transferService;

    }

    // afficher la page
    @GetMapping("/transfer")
    public String showAddContactForm(Model model) {

        model.addAttribute("contact", new Contact());

        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";
        model.addAttribute(
                "transfers",
                transferService.getTransfersOfUser(currentUser.getId())
        );
        model.addAttribute("currentUser", currentUser);

        return "transfer";
    }

    @GetMapping("/addcontact")
    public String showAddContactForm2(Model model) {

        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";

        model.addAttribute("contact", new Contact());

        // Récupère les contacts de l'utilisateur
        model.addAttribute("contacts", contactService.getContacts(currentUser.getId()));

        model.addAttribute("currentUser", currentUser);
        return "addcontact";
    }

    // ajouter contact
    @PostMapping("/addcontact")
    public String addContact(@RequestParam String email, Model model) {

        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";

        contactService.addContact(email, currentUser.getId());
        model.addAttribute("currentUser", currentUser);

        return "redirect:/transfer";
    }
}