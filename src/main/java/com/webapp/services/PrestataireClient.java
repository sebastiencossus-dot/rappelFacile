package com.webapp.services;

import com.webapp.models.Prestataires;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msjpa", contextId = "prestataireClient")
public interface PrestataireClient {

    @GetMapping("/prestataires")
    List<Prestataires> findAll();

    @GetMapping("/prestataires/{id}")
    Prestataires findById(@PathVariable Integer id);
}