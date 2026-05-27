package com.webapp.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msRdv")
public interface AdresseClient {

    @GetMapping("/adresses")
    List<Adresses> findAll();

    @GetMapping("/adresses/{id}")
    Adresses findById(@PathVariable Integer id);
}