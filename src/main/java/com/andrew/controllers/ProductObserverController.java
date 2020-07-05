package com.andrew.controllers;

import com.andrew.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProductObserverController {

    @Autowired
    ProductsRepository productsRepository;


    @GetMapping("/productObserver")
    public String getProductObserverPage(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        if (request.getParameterMap().containsKey("success")) {
            model.addAttribute("success", true);
        }
        return "product_observer";
    }

    @PostMapping("/updateProductById")
    public String getAddProductPage(Long productId,Model model, HttpServletRequest request) {
        if(productId==null){
            model.addAttribute("error",true);
            return "product_observer";
        }

       if(!productsRepository.existsById(productId)){
            model.addAttribute("error",true);
            return "product_observer";
        }
        System.err.println("sdsdf");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("productId",productId);
        return "redirect:/updateProduct";
    }


}
