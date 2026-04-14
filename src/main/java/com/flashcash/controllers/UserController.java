package com.flashcash.controllers;


import com.flashcash.models.User;

import com.flashcash.services.SessionService;
import com.flashcash.services.UserService;
import com.flashcash.services.form.SignUpForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;




@Controller
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;


    public UserController(UserService userService, SessionService sessionService) {
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





}