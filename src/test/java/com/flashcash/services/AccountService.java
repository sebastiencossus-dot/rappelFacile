package com.flashcash.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void creditAccount(Integer accountId, Double amount) {
        // Récupère le compte depuis la DB
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));

        // Ajoute le montant
        Double newAmount = account.getAmount() + amount;
        account.setAmount(newAmount);

        // Sauvegarde le compte
        accountRepository.save(account);
    }
}
