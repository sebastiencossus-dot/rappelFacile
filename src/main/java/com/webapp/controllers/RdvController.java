package com.webapp.controllers;

import com.webapp.models.RDV;
import com.webapp.models.User;
import com.webapp.services.RdvClient;
import com.webapp.services.RdvService;
import com.webapp.services.SessionService;
import com.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RdvController {

    @Autowired
    RdvService rdvService;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @GetMapping("/rdv")
    public ModelAndView index() {

        ModelAndView mav = new ModelAndView();

        User user = sessionService.sessionUser();
        if (user == null) {
            mav.setViewName("redirect:/login");
            return mav;
        }
        mav.addObject("currentUser", user);
        String email = user.getEmail();




        LocalDate today = LocalDate.now();

        List<RDV> rdvs = rdvService.getRdvByUser(user.getEmail());

        List<RDV> todayList = rdvs.stream()
                .filter(r -> r.getDateRdv().equals(today))
                .toList();

        List<RDV> attente = rdvs.stream()
                .filter(r -> r.getIsOK() == 1)
                .toList();

        List<RDV> autres = rdvs.stream()
                .filter(r -> r.getIsOK() == 2)
                .toList();

        mav.addObject("rdvJour", todayList);
        mav.addObject("rdvAttente", attente);
        mav.addObject("rdvAutres", autres);
        mav.setViewName("index");

        return mav;
    }
}