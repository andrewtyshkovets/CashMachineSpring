package com.andrew.repositories;

import com.andrew.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    public Product findByCode(Integer code);
    public Product findByName(String name);

}
