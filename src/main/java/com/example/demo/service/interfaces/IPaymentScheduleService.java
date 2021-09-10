package com.example.demo.service.interfaces;

import com.example.demo.entities.Credit;
import com.example.demo.entities.PaymentSchedule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IPaymentScheduleService {
    List<PaymentSchedule> getAll();
    PaymentSchedule getOne(UUID uuid);
    void save(Integer creditTime, BigDecimal creditAmount, String passportID, Credit credit);
    void update(PaymentSchedule paymentSchedule);
    void deleteById(UUID uuid);
    void delete(PaymentSchedule paymentSchedule);
    void saveAll(Set<PaymentSchedule> scheduleList);
    List<PaymentSchedule> getByPassportID(String passportID);
}
