package com.flashcash.repositories;

import com.flashcash.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {
    List<Transfer> findByFromIdOrderByDateDesc(Integer userId);

    List<Transfer> findByFromIdOrToId(Integer userId, Integer userId1);
}
