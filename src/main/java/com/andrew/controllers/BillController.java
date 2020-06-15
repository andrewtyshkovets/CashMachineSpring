package com.andrew.controllers;

import com.andrew.exceptions.NotEnoughProductException;
import com.andrew.models.Bill;
import com.andrew.models.BillBody;
import com.andrew.models.Product;
import com.andrew.repositories.BillBodyRepository;
import com.andrew.repositories.BillRepository;
import com.andrew.repositories.ProductsRepository;
import com.andrew.service.BillService;
import com.andrew.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillBodyRepository billBodyRepository;

    @Autowired
    ProductsRepository productsRepository;

    @GetMapping("/cashier/openBill")
    public String openBill(Model model,HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Bill bill;
        List<BillBody> billBodyList;
        if (httpSession.getAttribute("billId") == null) {
            Long userId = (Long) httpSession.getAttribute("user");
            Long cashBoxId = (Long) httpSession.getAttribute("cashBoxId");
            Long billId = billService.openBill(userId, cashBoxId);
            httpSession.setAttribute("billId", billId);
            bill = billRepository.getOne(billId);
            model.addAttribute("bill",bill);
            billBodyList = new ArrayList<>();
            Product product = Product.builder().id(0L).build();
            BillBody billBody = BillBody.builder().product(product).currentPrice(0.0).currentAmount(0.0).build();
            billBodyList.add(billBody);
            model.addAttribute("billBodies",billBodyList);
            return "bill";
        }
        bill = billRepository.getOne((Long)httpSession.getAttribute("billId"));
        billBodyList = billBodyRepository.findAllByBill(bill);
        model.addAttribute("bill",bill);
        model.addAttribute("billBodies",billBodyList);
        return "bill";
    }

    @GetMapping("/cashier/addProductByCode")
    public String getAddProductByCode(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        HttpSession httpSession = request.getSession();
        Long billId = (Long) httpSession.getAttribute("billId");
        if (billId != null) {
            return "add_product_to_bill_c";
        }
        return "redirect:/error";
    }

    @PostMapping("/cashier/addProductByCode")
    public String addProductByCode(Model model, Integer code, Double amount, HttpServletRequest request) {
        if (code == null || amount == null) {
            model.addAttribute("error", true);
            return "add_product_to_bill_c";
        }
        HttpSession httpSession = request.getSession();
        Long billId = (Long) httpSession.getAttribute("billId");
        if (billService.addProductToBillByCode(billId, code, amount)) {
            return "redirect:/cashier/openBill";
        }
        return "redirect:/error";
    }

    @GetMapping("/cashier/addProductByName")
    public String getAddProductByName(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }
        HttpSession httpSession = request.getSession();
        Long billId = (Long) httpSession.getAttribute("billId");
        if (billId != null) {
            return "add_product_to_bill_n";
        }
        return "redirect:/error";
    }

    @PostMapping("/cashier/addProductByName")
    public String addProductByName(Model model,String name, Double amount, HttpServletRequest request) {
        if (name.isEmpty() || amount == null || productsRepository.findByName(name)==null) {
            model.addAttribute("error", true);
            return "add_product_to_bill_n";
        }
        HttpSession httpSession = request.getSession();
        Long billId = (Long) httpSession.getAttribute("billId");
        System.err.println(billId);
        if (billService.addProductToBillByName(billId, name, amount)) {
            return "redirect:/cashier/openBill";
        }
        return "redirect:/error";
    }

    @PostMapping("/cashier/closeBill")
    public String closeBill(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        Long billId = (Long) httpSession.getAttribute("billId");
        try {
            if (billService.closeBill(billId)) {
                httpSession.setAttribute("billId", null);
                return "redirect:/cashier/cashBox";
            }
        } catch (NotEnoughProductException e) {
            e.printStackTrace();
            return "redirect:/error";// на сторінку продукт закінчився, соррі
        }
        return "redirect:/error";
    }
}
