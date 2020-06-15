package com.andrew.controllers;

import com.andrew.models.User;
import com.andrew.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class RoleController {

    @Autowired
    UsersRepository usersRepository;

    /*@GetMapping("/roleController")
    public String getLoginPage(){
         return "login";
    }

     */


    @GetMapping("/roleController")
    public String getAbilitiesPage(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Long userId = (Long) httpSession.getAttribute("user");
        User user = usersRepository.getOne(userId);
        String role = user.getRole().name();
        if (role.equals("CASHIER")) {
            return "redirect:/cashier";
        }
        if (role.equals("SENIOR_CASHIER")) {
            return "redirect:/seniorCashier";
        }
        if (role.equals("PRODUCT_OBSERVER")) {
            return "redirect:/productObserver";
        }
        if (role.equals("ADMIN")) {
            return "redirect:/admin";
        }
        return "redirect:/error";
    }

}
