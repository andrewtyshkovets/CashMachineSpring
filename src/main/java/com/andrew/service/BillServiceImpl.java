package com.andrew.service;

import com.andrew.exceptions.NotEnoughProductException;
import com.andrew.models.*;
import com.andrew.repositories.*;
import com.andrew.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillBodyRepository billBodyRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CashBoxRepository cashBoxRepository;

    @Autowired
    ProductsRepository productsRepository;


    @Override
    public Long openBill(Long userId, Long cashBoxId) {
        User user = usersRepository.getOne(userId);
        Optional<CashBox> cashBoxOptional = cashBoxRepository.findById(cashBoxId);
        if (user != null && cashBoxOptional.isPresent()) {
            Bill bill = Bill.builder()
                    .cashier(user)
                    .cashBox(cashBoxOptional.get())
                    .currentDate(LocalDateTime.now())
                    .isCancelled(false)
                    .isClosed(false)
                    .totalPrice(0.0)
                    .build();

            bill = billRepository.save(bill);
            return bill.getId();
        }
        return null;
    }

    @Override
    @Transactional
    public boolean closeBill(Long billId) throws NotEnoughProductException {
        Bill bill = billRepository.getOne(billId);
        CashBox cashBox = cashBoxRepository.getOne(bill.getCashBox().getId());
        Product product;
        Double totalPrice = 0.0;
        if (bill != null) {
            List<BillBody> billBodies = billBodyRepository.findAllByBill(bill);
            for (BillBody billBody : billBodies) {
                totalPrice = totalPrice + billBody.getCurrentPrice();
                product = productsRepository.getOne(billBody.getProduct().getId());
                product.setAmount(product.getAmount() - billBody.getCurrentAmount());
                if (product.getAmount() < 0) {
                    throw new NotEnoughProductException("Not enough product");
                }
                productsRepository.save(product);
            }
            cashBox.setCurrentDateTime(LocalDateTime.now());
            cashBox.setCurrentMoney(cashBox.getCurrentMoney() + totalPrice);
            bill.setTotalPrice(totalPrice);
            bill.setClosed(true);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean cancelBill(Long billId) {
        Bill bill = billRepository.getOne(billId);
        if (bill != null && bill.isClosed()) {
            CashBox cashBox = cashBoxRepository.getOne(bill.getCashBox().getId());
            Product product;
            Double totalPrice = bill.getTotalPrice();
            List<BillBody> billBodies = billBodyRepository.findAllByBill(bill);
            for (BillBody billBody : billBodies) {
                product = productsRepository.getOne(billBody.getProduct().getId());
                product.setAmount(product.getAmount() + billBody.getCurrentAmount());
                productsRepository.save(product);
            }
            cashBox.setCurrentDateTime(LocalDateTime.now());
            cashBox.setCurrentMoney(cashBox.getCurrentMoney() - totalPrice);
            bill.setCancelled(true);
            billRepository.save(bill);
            return true;
        }
        return false;
    }

    @Override
    public boolean addProductToBillByCode(Long billId, Integer code, Double amount) {
        Optional<Bill> optionalBill = billRepository.findById(billId);
        Product product = productsRepository.findByCode(code);
        if (optionalBill.isPresent() && product!=null) {
            BillBody billBody = BillBody.builder()
                    .bill(optionalBill.get())
                    .product(product)
                    .currentAmount(amount)
                    .currentPrice(amount * (product.getPrice()))
                    .build();
            billBody = billBodyRepository.save(billBody);
            Bill bill =  optionalBill.get();
            bill.setTotalPrice(optionalBill.get().getTotalPrice()+billBody.getCurrentPrice());
            billRepository.save(bill);
            return true;
        }
        return false;
    }

    @Override
    public boolean addProductToBillByName(Long billId, String name, Double amount) {
        Optional<Bill> optionalBill = billRepository.findById(billId);
        Product optionalProduct = productsRepository.findByName(name);
        if (optionalBill.isPresent() && optionalProduct!=null) {
            BillBody billBody = BillBody.builder()
                    .bill(optionalBill.get())
                    .product(optionalProduct)
                    .currentAmount(amount)
                    .currentPrice(amount * (optionalProduct.getPrice()))
                    .build();
            billBody = billBodyRepository.save(billBody);
            Bill bill =  optionalBill.get();
            bill.setTotalPrice(optionalBill.get().getTotalPrice()+billBody.getCurrentPrice());
            billRepository.save(bill);
            return true;
        }
        return false;
    }
}
