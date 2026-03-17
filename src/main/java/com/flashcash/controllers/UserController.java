package com.flashcash.controllers;


import com.flashcash.models.Transfer;
import com.flashcash.models.User;
import com.flashcash.services.TransferService;
import org.springframework.ui.Model;
import com.flashcash.services.SessionService;
import com.flashcash.services.UserService;
import com.flashcash.services.form.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;
    private final TransferService transferService;

    public UserController(UserService userService, SessionService sessionService, TransferService transferService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.transferService = transferService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        User user = sessionService.sessionUser(); // récupère l'utilisateur
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("currentUser", user); // passe à la vue
        return mav;
    }

    @GetMapping("/signup")
    public ModelAndView showRegisterForm() {
        return new ModelAndView("signup", "signUpForm", new SignUpForm());
    }

    @PostMapping("/signup")
    public ModelAndView processRequest(SignUpForm form) {
        userService.registration(form);
        return new ModelAndView("signin");
    }

    @GetMapping("/profile")
    public String profile(Model model) {

        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";

        List<Transfer> transfers =
                transferService.getUserTransfers(currentUser.getId());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("transfers", transfers);
        return "profile";
    }

    @PostMapping("/profile/password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {

        User currentUser = sessionService.sessionUser();
        if (currentUser == null) return "redirect:/signin";

        try {
            userService.changePassword(currentUser, oldPassword, newPassword, confirmPassword);
            redirectAttributes.addFlashAttribute("success", "Mot de passe modifié avec succès");

        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/profile";
    }

}