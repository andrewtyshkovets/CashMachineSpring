package com.andrew.repositories;

import com.andrew.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface TokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String value);
}
