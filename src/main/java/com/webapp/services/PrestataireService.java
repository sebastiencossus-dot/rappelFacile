package com.webapp.services;

import com.webapp.models.Prestataires;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestataireService {

    private final PrestataireClient client;

    public PrestataireService(PrestataireClient client) {
        this.client = client;
    }

    public List<Prestataires> findAll() {
        return client.findAll();
    }

    public Prestataires findById(Integer id) {
        return client.findById(id);
    }
}
