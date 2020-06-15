package com.andrew.controllers;

import com.andrew.models.CashBoxNumber;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class AdminController {

    @GetMapping("admin")
    public String getAdminPage(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        return "admin";
    }
}
