package com.flashcash.services;

import com.flashcash.models.Transfer;
import com.flashcash.models.User;
import com.flashcash.repositories.TransferRepository;
import com.flashcash.repositories.UserRepository;

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

    public void transferToContact(
            Integer senderId,
            Integer receiverId,
            Double amount) {

        User sender = userRepository
                .findById(senderId)
                .orElseThrow();

        User receiver = userRepository
                .findById(receiverId)
                .orElseThrow();

        double feeRate = 0.005; // 0.5 %
        double fee = amount * feeRate;

        double amountBeforeFee = amount + fee;

        if(sender.getAccount().getAmount() < amount){
            throw new RuntimeException("Insufficient balance");
        }

        // débit expéditeur
        sender.getAccount().setAmount(
                sender.getAccount().getAmount() - amountBeforeFee
        );

        // crédit destinataire
        receiver.getAccount().setAmount(
                receiver.getAccount().getAmount()
        );

        // création du transfert
        Transfer transfer = new Transfer();
        transfer.setFrom(sender);
        transfer.setTo(receiver);
        transfer.setDate(LocalDateTime.now());
        transfer.setAmountBeforeFee(amount);
        transfer.setAmountAfterFee(amountBeforeFee);

        transferRepository.save(transfer);

        userRepository.save(sender);
        userRepository.save(receiver);
    }
}