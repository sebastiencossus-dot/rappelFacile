package com.flashcash.services;

import com.flashcash.models.Account;
import com.flashcash.repositories.AccountRepository;
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

    @Transactional
    public void updateIban(Integer accountId, String iban) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));

        account.setIban(iban);  // on modifie l'IBAN

        accountRepository.save(account);
    }

    public void withdraw(Integer accountId, double amount){

        Account account = accountRepository.findById(accountId)
                .orElseThrow();

        if(account.getAmount() < amount){
            throw new RuntimeException("Solde insuffisant");
        }

        account.setAmount(account.getAmount() - amount);

        accountRepository.save(account);

        System.out.println("Virement vers IBAN : " + account.getIban());
    }

}