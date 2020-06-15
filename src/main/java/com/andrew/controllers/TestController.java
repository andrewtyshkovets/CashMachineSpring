package com.andrew.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    @GetMapping("/test")
    public String getTestPage(HttpServletRequest request){
        return "test";
    }
}
