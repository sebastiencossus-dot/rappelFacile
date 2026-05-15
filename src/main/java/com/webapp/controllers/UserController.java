package com.webapp.controllers;





import com.webapp.models.User;
import com.webapp.services.RdvClient;
import com.webapp.services.SessionService;
import com.webapp.services.UserService;
import com.webapp.services.form.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {

    @Autowired
    RdvClient rdvClient;

    private final UserService userService;
    private final SessionService sessionService;


    public UserController(com.webapp.services.UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;

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


    @GetMapping("/signin")
    public ModelAndView showLoginPage() {
        return new ModelAndView("signin");
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