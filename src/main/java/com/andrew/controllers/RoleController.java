package com.andrew.controllers;

import com.andrew.repositories.UsersRepository;
import com.andrew.security.details.UserDetailsImpl;
import com.andrew.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static com.andrew.transfer.UserDto.from;

@Controller
public class RoleController {

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/roleController")
    public String getAbilitiesPage(HttpServletRequest request,Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        UserDetailsImpl details = (UserDetailsImpl)authentication.getPrincipal();
        UserDto user = from(details.getUser());
        System.err.println(user);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user",user);
        String role = user.getRole();
        System.out.println(role);
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
