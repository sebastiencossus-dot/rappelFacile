package com.flashcash.controllers;

import com.flashcash.models.User;
import com.flashcash.services.ContactService;
import com.flashcash.services.SessionService;
import com.flashcash.services.TransferService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TransferController {

    private final SessionService sessionService;
    private final ContactService contactService;
    private final TransferService transferService;

    public TransferController(SessionService sessionService, ContactService contactService, TransferService transferService) {
        this.sessionService = sessionService;
        this.contactService = contactService;
        this.transferService = transferService;
    }

    @Controller
    public class transferController {

        private final SessionService sessionService;
        private final ContactService contactService;
        private final TransferService transferService;

        public transferController(SessionService sessionService,
                                  ContactService contactService,
                                  TransferService transferService) {
            this.sessionService = sessionService;
            this.contactService = contactService;
            this.transferService = transferService;
        }

        @GetMapping("/contacttransfer")
        public String showTransferForm(Model model) {
            User currentUser = sessionService.sessionUser();

            model.addAttribute("contacts", contactService.getContacts(currentUser.getId()));



            // ajout de la liste des transferts
            model.addAttribute(
                    "transfers",
                    transferService.getTransfersOfUser(currentUser.getId())
            );
            model.addAttribute("currentUser", currentUser);

            return "contacttransfer"; // page HTML à afficher
        }

        @PostMapping("/contacttransfer")
        public String transferMoney(@RequestParam Integer contactId,
                                    @RequestParam Double amount,
                                    Model model) {
            User currentUser = sessionService.sessionUser();

            try {
                transferService.transferToContact(currentUser.getId(), contactId, amount);
                model.addAttribute("message", "Transfer successful");
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
            }
            model.addAttribute("contacts", contactService.getContacts(currentUser.getId()));
            model.addAttribute("currentUser", currentUser);
            return "redirect:/contacttransfer";
        }
    }
}