package com.webapp.controllers;

import com.webapp.models.AdresseDTO;
import com.webapp.models.PrestataireDTO;
import com.webapp.models.Prestataires;
import com.webapp.models.Professions;
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
    private RestTemplate restTemplate;

    @GetMapping("/add")
    public String showForm(Model model) {

        Professions[] professions = restTemplate.getForObject(
                "http://localhost:8091/professions",
                Professions[].class
        );

        model.addAttribute("professions", professions);

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
            @RequestParam List<String> codePostals
    ) {
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
            a.setCodePostal(codePostals.get(i));
            adresses.add(a);
        }

        dto.setAdresses(adresses);

        restTemplate.postForObject(
                "http://localhost:8091/prestataires",
                dto,
                Void.class
        );

        return "redirect:/prestataires";
    }

    @GetMapping("/prestataires/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {

        Prestataires prestataire = restTemplate.getForObject(
                "http://localhost:8091/prestataires" + id,
                Prestataires.class
        );

        Professions[] professions = restTemplate.getForObject(
                "http://localhost:8091/professions",
                Professions[].class
        );

        model.addAttribute("prestataire", prestataire);
        model.addAttribute("professions", professions);

        return "prestataire-edit";
    }

    @PostMapping("/prestataires/edit/{id}")
    public String update(
            @PathVariable Integer id,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam(required = false) List<Integer> professionIds,
            @RequestParam(required = false) List<String> rues,
            @RequestParam(required = false) List<String> numeros,
            @RequestParam(required = false) List<String> villes,
            @RequestParam(required = false) List<String> codePostals
    ) {

        Map<String, Object> body = new HashMap<>();
        body.put("nom", nom);
        body.put("prenom", prenom);
        body.put("professionIds", professionIds);

        List<Map<String, String>> adresses = new ArrayList<>();

        if (rues != null) {
            for (int i = 0; i < rues.size(); i++) {
                Map<String, String> a = new HashMap<>();
                a.put("rue", rues.get(i));
                a.put("numero", numeros.get(i));
                a.put("ville", villes.get(i));
                a.put("codePostal", codePostals.get(i));
                adresses.add(a);
            }
        }

        body.put("adresses", adresses);

        restTemplate.put(
                "http://localhost:8091/prestataires" + id,
                body
        );

        return "redirect:/prestataires";
    }
}


