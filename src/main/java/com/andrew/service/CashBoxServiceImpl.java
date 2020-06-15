package com.andrew.service;

import com.andrew.models.CashBox;
import com.andrew.models.CashBoxNumber;
import com.andrew.models.Report;
import com.andrew.models.User;
import com.andrew.repositories.CashBoxNumberRepository;
import com.andrew.repositories.CashBoxRepository;
import com.andrew.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CashBoxServiceImpl implements CashBoxService {

    @Autowired
    CashBoxRepository cashBoxRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CashBoxNumberRepository cashBoxNumberRepository;

    @Autowired
    ReportService reportService;

    @Override
    public Long registerNewCashBox(Integer cashBoxNumber) {
        Optional<CashBoxNumber> cashBoxNumberOptional = cashBoxNumberRepository.getByNumber(cashBoxNumber);
        if (!cashBoxNumberOptional.isPresent()) {
            CashBoxNumber cashBoxNumber1 = CashBoxNumber.builder()
                    .number(cashBoxNumber).build();
            cashBoxNumber1 = cashBoxNumberRepository.save(cashBoxNumber1);
            return cashBoxNumber1.getId();
        }
        return null;
    }

    @Override
    public Long openCashBoxSession(Integer cashBoxNumber, Long userId, Double startMoney) {
        User user = usersRepository.getOne(userId);
        Optional<CashBoxNumber> cashBoxNumberOptional = cashBoxNumberRepository.getByNumber(cashBoxNumber);
        if (cashBoxNumberOptional.isPresent()) {
            CashBox cashBox = CashBox.builder()
                    .cashBoxNumber(cashBoxNumberOptional.get())
                    .startDateTime(LocalDateTime.now())
                    .StartMoney(startMoney)
                    .CurrentMoney(startMoney)
                    .currentDateTime(LocalDateTime.now())
                    .user(user)
                    .build();
            cashBox = cashBoxRepository.save(cashBox);
            return cashBox.getId();
        }
        return null;
    }

    @Override
    public Report closeCashBox(Long cashBoxId) {
        CashBox cashBox = cashBoxRepository.getOne(cashBoxId);
        if (cashBox!=null){
                cashBox.setFinishDateTime(LocalDateTime.now());
                cashBox.setFinishMoney(cashBox.getCurrentMoney());
                Report report = reportService.makeZReport(cashBoxId);
                return report;
        }
        return null;
    }


}
