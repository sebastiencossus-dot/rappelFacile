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
    MsJpaClient msJpaClient;

    @Autowired
    SessionService sessionService;

    public RdvService(MsJpaClient msJpaClient) {
        this.msJpaClient = msJpaClient;
    }

    public List<RDV> getRdvByUser(@RequestBody String email) {
        return msJpaClient.getRdvByUser(email);
    }

    public RDV createRdv(RDV rdv) {
        rdv.setIsOK(1);
        return msJpaClient.createRdv(rdv);
    }

    public RDV updateRdv(Integer id, RDV rdv) {
        User user = sessionService.sessionUser();
        return msJpaClient.updateRdv(id, rdv, user.getEmail());
    }

    public void deleteRdv(Integer id) {
        User user = sessionService.sessionUser();
        msJpaClient.deleteRdv(id, user.getEmail());
    }
}
