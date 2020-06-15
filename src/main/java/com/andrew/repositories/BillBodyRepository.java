package com.andrew.repositories;

import com.andrew.models.Bill;
import com.andrew.models.BillBody;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillBodyRepository extends JpaRepository<BillBody,Long> {
    List<BillBody> findAllByBill(Bill bill);
}
