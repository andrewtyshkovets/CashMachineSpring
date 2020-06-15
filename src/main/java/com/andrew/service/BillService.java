package com.andrew.service;

import com.andrew.exceptions.NotEnoughProductException;
import com.andrew.transfer.UserDto;

public interface BillService {
    public Long openBill(Long userId, Long cashBoxId);
    public boolean closeBill(Long billId) throws NotEnoughProductException;
    public boolean cancelBill(Long billId);
    public boolean addProductToBillByCode(Long billId,Integer code,Double amount);
    public boolean addProductToBillByName(Long billId,String name,Double amount);
}
