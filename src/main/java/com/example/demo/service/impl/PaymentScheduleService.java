package com.example.demo.service.impl;

import com.example.demo.dao.interfaces.IPaymentScheduleDao;
import com.example.demo.entities.PaymentSchedule;
import com.example.demo.service.interfaces.IPaymentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class PaymentScheduleService implements IPaymentScheduleService {

    IPaymentScheduleDao dao;

    @Autowired
    public void setDao(IPaymentScheduleDao daoToSet) {
        dao = daoToSet;
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
    public void save(PaymentSchedule paymentSchedule) {
        dao.save(paymentSchedule);
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

}
