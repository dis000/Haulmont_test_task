package com.example.demo.dao.impl;

import com.example.demo.dao.AbstractJpaDao;
import com.example.demo.dao.interfaces.IPaymentScheduleDao;
import com.example.demo.entities.PaymentSchedule;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class PaymentScheduleDao  extends AbstractJpaDao<PaymentSchedule, UUID> implements IPaymentScheduleDao {

    public PaymentScheduleDao() {
        setClazz(PaymentSchedule.class);
    }

    @PersistenceContext
    EntityManager entityManager;

    public List<PaymentSchedule> findByUUID(String passportID) {
        return entityManager.createQuery("select p from PaymentSchedule p where p.creditOffer.client.passportID='"+ passportID +"'",PaymentSchedule.class).getResultList();
    }
}
