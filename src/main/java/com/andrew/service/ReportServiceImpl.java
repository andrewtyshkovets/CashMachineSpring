package com.andrew.service;

import com.andrew.models.Bill;
import com.andrew.models.CashBox;
import com.andrew.models.Report;
import com.andrew.models.ReportType;
import com.andrew.repositories.BillRepository;
import com.andrew.repositories.CashBoxRepository;
import com.andrew.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    CashBoxRepository cashBoxRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    BillRepository billRepository;

    @Override
    public Report makeXReport(Long cashBoxId) {
        CashBox cashBox = cashBoxRepository.getOne(cashBoxId);
        if (cashBox != null) {
            Report report = Report.builder()
                    .type(ReportType.X_REPORT)
                    .cashBox(cashBox)
                    .startMoney(cashBox.getStartMoney())
                    .startTime(cashBox.getStartDateTime())
                    .finishMoney(cashBox.getCurrentMoney())
                    .finishTime(cashBox.getCurrentDateTime())
                    .sumOfSales(cashBox.getCurrentMoney() - cashBox.getStartMoney())
                    .build();
            report = reportRepository.save(report);
            return report;
        }
        return null;
    }

    @Override
    public Report makeZReport(Long cashBoxId) {
        CashBox cashBox = cashBoxRepository.getOne(cashBoxId);
        if (cashBox != null) {
            List<Bill> bills = billRepository.getAllByCashBox(cashBox);
            int totalClosed = 0;
            int totalCancelled = 0;
            for (Bill bill : bills) {
                if (bill.isClosed() && !bill.isCancelled()) {
                    totalClosed += 1;
                }
                if (bill.isCancelled()) {
                    totalCancelled += 1;
                }
            }

            Report report = Report.builder()
                    .type(ReportType.Z_REPORT)
                    .cashBox(cashBox)
                    .startMoney(cashBox.getStartMoney())
                    .startTime(cashBox.getStartDateTime())
                    .finishMoney(cashBox.getFinishMoney())
                    .finishTime(cashBox.getFinishDateTime())
                    .sumOfSales(cashBox.getFinishMoney() - cashBox.getStartMoney())
                    .totalClosed(totalClosed)
                    .totalCancelled(totalCancelled)
                    .build();
            report = reportRepository.save(report);
            return report;
        }
        return null;
    }
}
