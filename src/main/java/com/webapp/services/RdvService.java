package com.webapp.services;

import com.webapp.models.User;
import com.webapp.models.RDV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RdvService {

    @Autowired
    MsRdvClient msRdvClient;

    @Autowired
    SessionService sessionService;

    public RdvService(MsRdvClient msRdvClient) {
        this.msRdvClient = msRdvClient;
    }

    public List<RDV> getRdvByUser(@RequestBody String email) {
        return msRdvClient.getRdvByUser(email);
    }

    public RDV createRdv(RDV rdv) {
        rdv.setIsOK(1);
        return msRdvClient.createRdv(rdv);
    }

    public RDV updateRdv(Integer id, RDV rdv) {
        User user = sessionService.sessionUser();
        return msRdvClient.updateRdv(id, rdv, user.getEmail());
    }

    public void deleteRdv(Integer id) {
        User user = sessionService.sessionUser();
        msRdvClient.deleteRdv(id, user.getEmail());
    }
}
