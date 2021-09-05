package com.example.demo.dao.interfaces;

import com.example.demo.dao.IAbstractJpaDao;
import com.example.demo.entities.PaymentSchedule;

import java.util.List;
import java.util.UUID;

public interface IPaymentScheduleDao extends IAbstractJpaDao<PaymentSchedule, UUID> {
    List<PaymentSchedule> findByUUID(String passportID);
}
