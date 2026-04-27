package com.flashcash.repositories;

import com.flashcash.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartsRepository extends JpaRepository<User, Integer> {
}
