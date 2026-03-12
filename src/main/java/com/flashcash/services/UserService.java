package com.flashcash.services;

import com.flashcash.models.Account;
import com.flashcash.models.User;
import com.flashcash.repositories.AccountRepository;
import com.flashcash.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.flashcash.services.form.SignUpForm;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                       AccountRepository accountRepository) {
//        this.sessionService = sessionService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public User registration(SignUpForm form) {
        User user = new User();
        Account account = new Account();
        account.setAmount(0.0);
        user.setAccount(account);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        return userRepository.save(user);


    }
}
