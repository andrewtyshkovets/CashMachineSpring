package com.andrew.controllers;

import com.andrew.repositories.CashBoxNumberRepository;
import com.andrew.service.CashBoxService;
import com.andrew.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CashBoxController {

    @Autowired
    CashBoxService cashBoxService;

    @Autowired
    CashBoxNumberRepository cashBoxNumberRepository;

    @GetMapping("/newCashBox")
    public String getNewCashBoxPage() {
        return "newCashBox";
    }

    @PostMapping("/newCashBox")
    public String registerCashBox(Integer number) {
        cashBoxService.registerNewCashBox(number);
        return "admin";
    }


    @GetMapping("/cashier/openCashBox")
    public String openCashBox(Model model, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Integer number = (Integer) httpSession.getAttribute("cashBoxNumber");
        Double startMoney = (Double) httpSession.getAttribute("startMoney");
        UserDto user = (UserDto) httpSession.getAttribute("user");
        Long userId = user.getId();
        Long id = cashBoxService.openCashBoxSession(number, userId, startMoney);
        httpSession.setAttribute("cashBoxId", id);
        return "cashbox";
    }

    @PostMapping("/cashier/closeCashBox")
    public String closeCashBox(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Long id = (Long) httpSession.getAttribute("cashBoxId");
        if (cashBoxService.closeCashBox(id) != null) {
            httpSession.setAttribute("cashBoxId", null);
            return "redirect:/cashier";
        }
        return "redirect:/error";
    }

    @GetMapping("/cashier/cashBox")
    public String getCashBoxPage(Model model, HttpServletRequest request) {
        return "cashbox";
    }


}
