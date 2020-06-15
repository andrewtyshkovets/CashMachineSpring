package com.andrew.controllers;

import com.andrew.models.CashBoxNumber;
import com.andrew.repositories.CashBoxNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CashierController {

    @Autowired
    CashBoxNumberRepository cashBoxNumberRepository;

    @GetMapping("/cashier")
    public String getCashierPage(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        List<CashBoxNumber> cashBoxes = cashBoxNumberRepository.findAll();
        model.addAttribute("cashBoxes",cashBoxes);
        return "cashier";
    }

    @PostMapping("/cashier")
    public String openCashBox(Model model,Integer number, Double startMoney, HttpServletRequest request){
        if(number==null || startMoney==null){
            model.addAttribute("error", true);
            List<CashBoxNumber> cashBoxes = cashBoxNumberRepository.findAll();
            model.addAttribute("cashBoxes",cashBoxes);
            return "cashier";
        } else {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("cashBoxNumber",number);
            httpSession.setAttribute("startMoney",startMoney);
            return "redirect:/cashier/openCashBox";
        }
    }

}
