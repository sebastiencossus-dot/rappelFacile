package com.webapp.services;


import com.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    MsRdvClient msRdvClient;






    public User sessionUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null ||
                !auth.isAuthenticated() ||
                "anonymousUser".equals(auth.getName())) {
            throw new RuntimeException("NO_SESSION");
        }

        return msRdvClient.findUserByEmail(auth.getName());
    }
}