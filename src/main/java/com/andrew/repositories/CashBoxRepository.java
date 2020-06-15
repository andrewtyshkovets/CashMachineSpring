package com.andrew.repositories;

import com.andrew.models.CashBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CashBoxRepository extends JpaRepository<CashBox,Long> {
    public List<CashBox> findAllByCashBoxNumber(Integer number);
}
