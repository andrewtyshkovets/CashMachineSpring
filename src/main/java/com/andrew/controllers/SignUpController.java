package com.andrew.controllers;


import com.andrew.forms.UserForm;
import com.andrew.repositories.UsersRepository;
import com.andrew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SignUpController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserService userService;

    @GetMapping("/signUp")
    public String getSignUpPage() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String getSignUpPage(Model model,UserForm userForm) {
        String regex = "^[a-z]{3,}\\d*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userForm.getUsername());
        if(!m.matches()){
            model.addAttribute("error",true);
            return "signUp";
        }
        if(usersRepository.findByUsername(userForm.getUsername()).isPresent()){
            model.addAttribute("isUser",true);
            return "signUp";
        }
        userService.signUp(userForm);
        return "redirect:/login";
    }


}
