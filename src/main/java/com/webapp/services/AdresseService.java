package com.webapp.services;

import com.webapp.models.Adresses;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseService {

    private final AdresseClient client;

    public AdresseService(AdresseClient client) {
        this.client = client;
    }

    public List<Adresses> findAll() {
        return client.findAll();
    }

    public Adresses findById(Integer id) {
        return client.findById(id);
    }
}