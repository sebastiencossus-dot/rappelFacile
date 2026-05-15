package com.webapp.services;


import com.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    RdvClient rdvClient;






    public User sessionUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            throw new RuntimeException("Utilisateur non connecté");
        }
        return rdvClient.findUserByEmail(auth.getName());
    }
}