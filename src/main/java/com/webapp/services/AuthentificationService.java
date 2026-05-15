package com.webapp.services;



import com.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class AuthentificationService implements UserDetailsService {

    @Autowired
    RdvClient rdvClient;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            User user = rdvClient.findUserByEmail(email);

            if (user == null || user.getEmail() == null || user.getMdp() == null) {
                throw new UsernameNotFoundException("Utilisateur invalide");
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getMdp(),
                    new ArrayList<>()
            );

        } catch (Exception e) {
            throw new UsernameNotFoundException("Erreur auth user: " + email, e);
        }
    }
}