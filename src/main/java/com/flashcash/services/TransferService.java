package com.flashcash.services;

import com.flashcash.models.Transfer;
import com.flashcash.models.User;
import com.flashcash.repositories.TransferRepository;
import com.flashcash.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService {

    private final UserRepository userRepository;
    private final TransferRepository transferRepository;

    public TransferService(UserRepository userRepository,
                           TransferRepository transferRepository) {

        this.userRepository = userRepository;
        this.transferRepository = transferRepository;
    }

    // Récupère tous les transferts où l'utilisateur est l'expéditeur
    public List<Transfer> getTransfersOfUser(Integer userId) {
        return transferRepository.findByFromIdOrToId(userId, userId);
    }

    @Transactional
    public void transferToContact(
            Integer senderId,
            Integer receiverId,
            Double amount) {

        User sender = userRepository.findById(senderId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();

        if (sender.getId().equals(receiver.getId())) {
            throw new RuntimeException("Impossible de se transférer de l'argent à soi-même");
        }

        double feeRate = 0.005; // 0.5 %
        double fee = amount * feeRate;

        double totalDebit = amount + fee;     // ce que paie l'expéditeur
        double receivedAmount = amount;       // ce que reçoit le destinataire

        // Vérification du solde
        if (sender.getAccount().getAmount() < totalDebit) {
            throw new RuntimeException("Solde insuffisant");
        }

        // 💸 Débit expéditeur (montant + frais)
        sender.getAccount().setAmount(
                sender.getAccount().getAmount() - totalDebit
        );

        // 💰 Crédit destinataire (montant sans frais)
        receiver.getAccount().setAmount(
                receiver.getAccount().getAmount() + receivedAmount
        );

        // 🧾 Création du transfert
        Transfer transfer = new Transfer();
        transfer.setFrom(sender);
        transfer.setTo(receiver);
        transfer.setDate(LocalDateTime.now());
        transfer.setAmountBeforeFee(amount);        // montant envoyé
        transfer.setAmountAfterFee(totalDebit);     // montant payé

        transferRepository.save(transfer);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    public List<Transfer> getUserTransfers(Integer userId) {
        return transferRepository
                .findByFromIdOrToIdOrderByDateDesc(userId, userId);
    }
}