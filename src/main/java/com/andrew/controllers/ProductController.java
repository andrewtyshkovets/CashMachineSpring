package com.andrew.controllers;

import com.andrew.forms.ProductForm;
import com.andrew.models.Product;
import com.andrew.repositories.ProductsRepository;
import com.andrew.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ProductController {
    @Autowired
    ProductsService productsService;

    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("/products")
    public String getProductsPage(Model model) {
        List<Product> products = productsRepository.findAll();
        model.addAttribute("products",products);
        return "products";
    }

    @GetMapping("/addProduct")
    public String getAddProductPage(Model model,HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String getAddProductPage(Model model,HttpServletRequest request,ProductForm productForm) {
        System.err.println(productForm.getCode()+" "+productForm.getName()+" "+productForm.getAmount()+" "+productForm.getMeasure()+" "+productForm.getPrice());

        if(productForm.getAmount()==null || productForm.getCode()==null
        || productForm.getName().isEmpty() || productForm.getPrice()==null){
            model.addAttribute("error", true);
            return "addProduct";
        }
        Product product = productsRepository.findByName(productForm.getName());
        Product product1 = productsRepository.findByCode(productForm.getCode());
        if(product!=null || product1!=null){
            model.addAttribute("error", true);
            return "addProduct";
        }
          productsService.addProductToWarehouse(productForm);
        return "redirect:/products";
    }

    @GetMapping("/updateProduct")
    public String getUpdateProductPage(Model model, HttpServletRequest request){
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        Long productId = (Long) request.getSession().getAttribute("productId");
        Product product = productsRepository.getOne(productId);
        model.addAttribute("product",product);
        return "update_product_info";
    }


    @PostMapping("/updateProduct")
    public String updateProduct(Model model, HttpServletRequest request,Double amount){
        System.err.println(amount);
        if(amount==null || amount<0){
            model.addAttribute("error", true);
            Long productId = (Long) request.getSession().getAttribute("productId");
            Product product = productsRepository.getOne(productId);
            model.addAttribute("product",product);
            return "update_product_info";
        }
        Long productId = (Long) request.getSession().getAttribute("productId");
        System.err.println(productId+" "+amount);
        productsService.updateProductById(productId,amount);
        return "redirect:/products";
    }
}
