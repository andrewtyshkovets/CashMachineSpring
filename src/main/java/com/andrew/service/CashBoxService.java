package com.andrew.service;

import com.andrew.models.Report;

public interface CashBoxService {
    public Long registerNewCashBox(Integer cashBoxNumber);
    public Long openCashBoxSession(Integer cashBoxNumber, Long userId, Double startMoney);
    public Report closeCashBox(Long cashBoxId);
}
