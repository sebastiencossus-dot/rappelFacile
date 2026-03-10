package com.flashcash.controllers;

import ch.qos.logback.core.model.Model;
import com.flashcash.models.Transfer;
import com.flashcash.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {


    @GetMapping("/")
    public ModelAndView home(Model model) {
        User user = sessionService.sessionUser();
        model.addAttribute("user", user);
        List<Transfer> transactions = transferService.findTransactions();
        model.addAttribute("transfers", transactions);
        return new ModelAndView("index");
    }

}
