package com.andrew.controllers;

import com.andrew.forms.UserForm;
import com.andrew.models.User;
import com.andrew.service.UserService;
import com.andrew.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String getLoginPage(ModelMap model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "login";
    }
/*
    @PostMapping("/login")
    public String GetLoginPage(Model model, UserForm userForm, HttpServletRequest request) {
        User user = userService.signIn(userForm);
        if (user!=null) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user.getId());
            return "redirect:/roleController";
        } else {
            model.addAttribute("error",true);
            return "login";
        }
    }


 */

    @PostMapping("/login")
    public String login(Model model, UserForm loginForm, HttpServletRequest request) {
        User user = userService.signIn(loginForm);
        request.setAttribute("token",userService.login(loginForm));
        System.out.println(ResponseEntity.ok(userService.login(loginForm)).toString());
        if (user!=null) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user.getId());
            return "redirect:/roleController";
        } else {
            model.addAttribute("error",true);
            return "login";
        }


    }





    @GetMapping("/logOut")
    public String LogOut(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user", null);
        return "login";
    }


}
