package com.webapp.controllers;

import com.webapp.models.AdresseDTO;
import com.webapp.models.PrestataireDTO;
import com.webapp.models.Prestataires;
import com.webapp.models.Professions;
import com.webapp.services.PrestataireClient;
import com.webapp.services.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/prestataires")
public class PrestataireController {

    @Autowired
    private PrestataireClient prestataireClient;

    @Autowired
    private ProfessionService professionService;

    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("professions", professionService.findAll());
        return "prestataire-add";
    }

    @PostMapping("/add")
    public String createPrestataire(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam(required = false) List<Integer> professionIds,
            @RequestParam List<String> rues,
            @RequestParam List<String> numeros,
            @RequestParam List<String> villes,
            @RequestParam List<String> codePostals) {

        PrestataireDTO dto = new PrestataireDTO();
        dto.setNom(nom);
        dto.setPrenom(prenom);
        dto.setProfessionIds(professionIds);

        List<AdresseDTO> adresses = new ArrayList<>();
        for (int i = 0; i < rues.size(); i++) {
            AdresseDTO a = new AdresseDTO();
            a.setRue(rues.get(i));
            a.setNumero(numeros.get(i));
            a.setVille(villes.get(i));
            a.setCodepostal(codePostals.get(i));
            adresses.add(a);
        }
        dto.setAdresses(adresses);

        prestataireClient.create(dto);

        return "redirect:/rdv";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("prestataire", prestataireClient.findById(id));
        model.addAttribute("professions", professionService.findAll());
        return "prestataire-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @PathVariable Integer id,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam(required = false) List<Integer> professionIds,
            @RequestParam(required = false) List<String> rues,
            @RequestParam(required = false) List<String> numeros,
            @RequestParam(required = false) List<String> villes,
            @RequestParam(required = false) List<String> codePostals) {

        PrestataireDTO dto = new PrestataireDTO();
        dto.setNom(nom);
        dto.setPrenom(prenom);
        dto.setProfessionIds(professionIds);

        List<AdresseDTO> adresses = new ArrayList<>();
        if (rues != null) {
            for (int i = 0; i < rues.size(); i++) {
                AdresseDTO a = new AdresseDTO();
                a.setRue(rues.get(i));
                a.setNumero(numeros.get(i));
                a.setVille(villes.get(i));
                a.setCodepostal(codePostals.get(i));
                adresses.add(a);
            }
        }
        dto.setAdresses(adresses);

        prestataireClient.update(id, dto);

        return "redirect:/rdv";
    }
}


