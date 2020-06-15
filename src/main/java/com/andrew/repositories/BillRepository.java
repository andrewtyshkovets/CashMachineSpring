package com.andrew.repositories;

import com.andrew.models.Bill;
import com.andrew.models.CashBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Long> {
    List<Bill> getAllByCashBox (CashBox cashBox);
}
