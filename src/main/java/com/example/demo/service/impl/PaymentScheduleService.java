package com.example.demo.service.impl;

import com.example.demo.dao.interfaces.IPaymentScheduleDao;
import com.example.demo.entities.Credit;
import com.example.demo.entities.CreditOffer;
import com.example.demo.entities.PaymentSchedule;
import com.example.demo.service.interfaces.IClientService;
import com.example.demo.service.interfaces.ICreditOfferService;
import com.example.demo.service.interfaces.ICreditService;
import com.example.demo.service.interfaces.IPaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentScheduleService implements IPaymentScheduleService {

    IPaymentScheduleDao dao;
    ICreditOfferService creditOfferService;
    IClientService clientService;
    ICreditService creditService;

    @Autowired
    public void setDao(IPaymentScheduleDao daoToSet,
                       ICreditOfferService creditOfferService,
                       IClientService clientService,
                       ICreditService creditService) {
        dao = daoToSet;
        this.creditOfferService = creditOfferService;
        this.clientService = clientService;
        this.creditService = creditService;
    }

    @Override
    public List<PaymentSchedule> getAll() {
        return dao.findAll();
    }

    @Override
    public PaymentSchedule getOne(UUID uuid) {
        return dao.findOne(uuid);
    }



    @Override
    public void update(PaymentSchedule paymentSchedule) {
        dao.update(paymentSchedule);

    }

    @Override
    public void deleteById(UUID uuid) {
        dao.deleteById(uuid);
    }

    @Override
    public void delete(PaymentSchedule paymentSchedule) {
        dao.delete(paymentSchedule);
    }

    @Override
    public void saveAll(Set<PaymentSchedule> scheduleList) {

        for (PaymentSchedule schedule:
             scheduleList) {
            dao.update(schedule);
        }

    }

    @Override
    public List<PaymentSchedule> getByPassportID(String passportID) {
        return dao.findByUUID(passportID);
    }

    @Override
    public void save(Integer creditTime, BigDecimal creditAmount, String passportID, Credit credit) {

        credit = creditService.getOne(credit.getID());

        CreditOffer creditOffer = new CreditOffer(credit, clientService.getByPassportID(passportID), creditAmount);
        creditOfferService.save(creditOffer);

        BigDecimal percentOfLoan = getPercentOfLoan(creditAmount, credit.getPercentRate(), creditTime);

        BigDecimal paymentPerMonth = divideByMonth(percentOfLoan.add(creditAmount), creditTime);
        BigDecimal paymentPerMonthBody = divideByMonth(creditAmount, creditTime);
        BigDecimal paymentPerMonthPercent = divideByMonth(percentOfLoan, creditTime);

        for (int i = 0; i < creditTime*12; i++) {
            dao.update(new PaymentSchedule(
                    LocalDate.now().plusMonths(i+1),
                    paymentPerMonth,
                    paymentPerMonthBody,
                    paymentPerMonthPercent,
                    creditOffer));
        }
    }


    private BigDecimal getPercentOfLoan(BigDecimal creditAmount, BigDecimal percent, Integer creditTime) {
        BigDecimal dividedPercent = percent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_DOWN);
        BigDecimal percentOfLoan = creditAmount.multiply(dividedPercent);
        return percentOfLoan.multiply(BigDecimal.valueOf(creditTime));
    }


    private BigDecimal divideByMonth(BigDecimal value, Integer creditTime) {
        return value.divide(BigDecimal.valueOf(creditTime*12), 2, RoundingMode.HALF_DOWN);
    }
}
