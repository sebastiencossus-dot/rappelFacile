package com.webapp.controllers;

import com.webapp.models.RDV;
import com.webapp.models.User;
import com.webapp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Controller
public class RdvController {

    @Autowired
    RdvService rdvService;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @Autowired
    MsRdvClient msRdvClient;

    @Autowired
    ProfessionService professionService;

    @Autowired
    PrestataireService prestataireService;

    @Autowired
    AdresseService adresseService;



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
                .filter(r -> r.getDateRdv().toLocalDate().equals(today))
                .sorted(Comparator.comparing(RDV::getDateRdv)) // tri par heure croissante
                .toList();

        List<RDV> attente = rdvs.stream()
                .filter(r -> r.getIsOK() == 1 && r.getDateRdv().toLocalDate().isBefore(today))
                .sorted(Comparator.comparing(RDV::getDateRdv)) // du plus ancien au plus récent
                .toList();

        List<RDV> autres = rdvs.stream()
                .filter(r -> r.getIsOK() == 2 && r.getDateRdv().toLocalDate().isAfter(today))
                .sorted(Comparator.comparing(RDV::getDateRdv)) // du plus proche au plus loin
                .toList();

        mav.addObject("rdvJour", todayList);
        mav.addObject("rdvAttente", attente);
        mav.addObject("rdvAutres", autres);
        mav.setViewName("index");

        return mav;
    }

    @GetMapping("/rdv/add")
    public ModelAndView addRdvForm() {

        ModelAndView mav = new ModelAndView("rdv-form");

        mav.addObject("rdv", new RDV());

        mav.addObject("prestataires", prestataireService.findAll());
        mav.addObject("professions", professionService.findAll());
        mav.addObject("adresse", adresseService.findAll());

        return mav;
    }

    @PostMapping("/rdv/add")
    public String createRdv(RDV rdv,
                            @RequestParam Integer prestataires,
                            @RequestParam Integer professions,
                            @RequestParam Integer adresses) {

        rdv.setIsOK(1);

        rdv.setPrestataires(prestataireService.findById(prestataires));
        rdv.setProfessions(professionService.findById(professions));
        rdv.setAdresses(adresseService.findById(adresses));

        rdv.setUser(sessionService.sessionUser());

        rdvService.createRdv(rdv);

        return "redirect:/rdv";
    }

    @PostMapping("/rdv/update/{id}")
    public String updateRdv(@PathVariable Integer id, RDV rdv) {
        rdvService.updateRdv(id, rdv);
        return "redirect:/rdv";
    }

    @GetMapping("/rdv/delete/{id}")
    public String deleteRdv(@PathVariable Integer id) {
        rdvService.deleteRdv(id);
        return "redirect:/rdv";
    }

    @GetMapping("/rdv/edit/{id}")
    public ModelAndView editRdv(@PathVariable Integer id) {

        String email = sessionService.sessionUser().getEmail();

        RDV rdv = msRdvClient.getRdv(id, email);

        if (rdv == null) {
            return new ModelAndView("redirect:/rdv");
        }

        return new ModelAndView("rdv-form", "rdv", rdv);
    }

}