package com.andrew.service;

import com.andrew.forms.ProductForm;

public interface ProductsService {
    public void addProductToWarehouse(ProductForm productForm);
    public void updateProductById(Long productId,Double amount);

}
