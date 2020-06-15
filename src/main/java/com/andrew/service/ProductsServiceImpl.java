package com.andrew.service;

import com.andrew.forms.ProductForm;
import com.andrew.models.Product;
import com.andrew.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    ProductsRepository productsRepository;

    @Override
    public void addProductToWarehouse(ProductForm productForm) {
        Product product = Product.builder()
                .code(productForm.getCode())
                .name(productForm.getName())
                .amount(productForm.getAmount())
                .measure(productForm.getMeasure())
                .price(productForm.getPrice())
                .build();
        productsRepository.save(product);
    }

    @Override
    public void updateProductById(Long productId, Double amount) {
        Product optionalProduct = productsRepository.getOne(productId);
        if (optionalProduct!=null) {
            optionalProduct.setAmount(amount);
            productsRepository.save(optionalProduct);
        }
    }

}
