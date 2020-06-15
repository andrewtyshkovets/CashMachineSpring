package com.andrew.controllers;

import com.andrew.models.Report;
import com.andrew.repositories.BillRepository;
import com.andrew.repositories.CashBoxRepository;
import com.andrew.service.BillService;
import com.andrew.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SeniorCashierController {

    @Autowired
    BillRepository billRepository;

    @Autowired
    CashBoxRepository cashBoxRepository;

    @Autowired
    BillService billService;

    @Autowired
    ReportService reportService;


    @GetMapping("/seniorCashier")
    public String getSeniorCashierPage(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        if (request.getParameterMap().containsKey("success")) {
            model.addAttribute("success", true);
        }
        return "senior_cashier";
    }

    @PostMapping("/seniorCashier/cancelBill")
    public String cancelBill(Model model,Long billId, HttpServletRequest request){
        if(billId==null || billRepository.getOne(billId)==null){
            model.addAttribute("error", true);
            return "senior_cashier";
        } else {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("billId",billId);
            return "redirect:/seniorCashier/cancelBill";
        }
    }

    @GetMapping("/seniorCashier/cancelBill")
    public String cancelBill(Model model,HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Long billId = (Long) httpSession.getAttribute("billId");
        if (billService.cancelBill(billId)) {
            model.addAttribute("success",true);
            return "senior_cashier";
        }
        return "redirect:/error";
    }

    @PostMapping("/seniorCashier/makeX")
    public String makeXReport(Model model,Long cashBoxId, HttpServletRequest request){
        if(cashBoxId==null || cashBoxRepository.getOne(cashBoxId)==null){
            model.addAttribute("error", true);
            return "senior_cashier";
        } else {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("cashBoxId",cashBoxId);
            return "redirect:/seniorCashier/getXReport";
        }
    }

    @GetMapping("/seniorCashier/getXReport")
    public String getXReport(Model model,HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Long cashBoxId = (Long) httpSession.getAttribute("cashBoxId");
        if (cashBoxId != null) {
            Report report = reportService.makeXReport(cashBoxId);
            if (report != null) {
                model.addAttribute("report",report);
                System.err.println(report);
                return "xReport";
            }
        }
        return "redirect:/error";
    }


    @PostMapping("/seniorCashier/makeZ")
    public String makeZReport(Model model,Long cashBoxId, HttpServletRequest request){
        if(cashBoxId==null || cashBoxRepository.getOne(cashBoxId)==null){
            model.addAttribute("error", true);
            return "senior_cashier";
        } else {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("cashBoxId",cashBoxId);
            return "redirect:/seniorCashier/getZReport";
        }
    }

    @GetMapping("/seniorCashier/getZReport")
    public String getZReport(Model model,HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Long cashBoxId = (Long) httpSession.getAttribute("cashBoxId");
        if (cashBoxId != null) {
            Report report = reportService.makeZReport(cashBoxId);
            if (report != null) {
                model.addAttribute("report",report);
                System.err.println(report);
                return "z-report";
            }
        }
        return "redirect:/error";
    }


}
