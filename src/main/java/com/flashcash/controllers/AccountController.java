package com.flashcash.controllers;



import com.flashcash.models.User;
import com.flashcash.repositories.AccountRepository;
import com.flashcash.services.AccountService;
import com.flashcash.services.SessionService;
import com.flashcash.services.form.PayCardForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class AccountController {

    private final SessionService sessionService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(SessionService sessionService, AccountService accountService, AccountRepository accountRepository) {
        this.sessionService = sessionService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/paycard")
    public String payCardForm(Model model) {
        User currentUser = sessionService.sessionUser();
        if (currentUser == null) {
            return "redirect:/signin"; // pas connecté
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("payCardForm", new PayCardForm());
        return "paycard";
    }

    @PostMapping("/paycard")
    public String submitPayCard(
            @ModelAttribute("payCardForm") PayCardForm payCardForm,
            BindingResult result,
            Model model
    ) {
        User currentUser = sessionService.sessionUser();
        if (currentUser == null) {
            return "redirect:/signin"; // pas connecté
        }

        if (result.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            return "paycard";
        }

        // Crédite le compte
        accountService.creditAccount(currentUser.getId(), payCardForm.getAmount());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("message", "Votre compte a été crédité de " + payCardForm.getAmount() + "€");
        model.addAttribute("payCardForm", new PayCardForm()); // reset formulaire
        return "index";
    }

    @GetMapping("/account/iban")
    public String ibanForm(Model model) {
        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("account", currentUser.getAccount());
        return "addiban";
    }

    @PostMapping("/account/iban")
    public String ibanSubmit(@RequestParam String iban, Model model) {
        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";

        accountService.updateIban(currentUser.getAccount().getAccountId(), iban);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("account", currentUser.getAccount());
        model.addAttribute("successIban", true);
        return "addiban";
    }

    @GetMapping("/banktransfer")
    public String showTransfer(Model model) {

        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("account", currentUser.getAccount());

        return "banktransfer";
    }

    @PostMapping("/banktransfer/withdraw")
    public String withdraw(
            @RequestParam Integer accountId,
            @RequestParam double amount,
            Model model) {

        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";
        accountService.withdraw(accountId, amount);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("message", "Retrait effectué");

        return "redirect:/";
    }
}