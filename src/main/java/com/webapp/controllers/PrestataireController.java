package com.webapp.controllers;

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
    public String addPrestataire(
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
                Map<String, String> adr = new HashMap<>();
                adr.put("rue", rues.get(i));
                adr.put("numero", numeros.get(i));
                adr.put("ville", villes.get(i));
                adr.put("codePostal", codePostals.get(i));
                adresses.add(adr);
            }
        }

        body.put("adresses", adresses);

        restTemplate.postForObject(
                "http://localhost:8091/professions",
                body,
                String.class
        );

        return "redirect:/prestataires";
    }

    @GetMapping("/prestataires/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {

        Prestataires prestataire = restTemplate.getForObject(
                "http://localhost:8091/professions" + id,
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
                "http://localhost:8091/professions" + id,
                body
        );

        return "redirect:/prestataires";
    }
}


