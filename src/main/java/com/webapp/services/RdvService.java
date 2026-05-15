package com.webapp.services;

import com.webapp.models.User;
import com.webapp.services.RdvClient;
import com.webapp.models.RDV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RdvService {

    @Autowired
    RdvClient rdvClient;

    public RdvService(RdvClient rdvClient) {
        this.rdvClient = rdvClient;
    }

    public List<RDV> getRdvByUser(@RequestBody String email) {
        return rdvClient.getRdvByUser(email);
    }
}
