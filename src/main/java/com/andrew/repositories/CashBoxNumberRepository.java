package com.andrew.repositories;

import com.andrew.models.CashBoxNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashBoxNumberRepository extends JpaRepository<CashBoxNumber,Long> {
    public Optional<CashBoxNumber> getByNumber(Integer number);
}
