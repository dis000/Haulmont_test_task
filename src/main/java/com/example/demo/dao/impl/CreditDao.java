package com.example.demo.dao.impl;

import com.example.demo.dao.AbstractJpaDao;
import com.example.demo.dao.interfaces.ICreditDao;
import com.example.demo.entities.Credit;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class CreditDao extends AbstractJpaDao<Credit, UUID> implements ICreditDao {

    public CreditDao() {
        setClazz(Credit.class);
    }

    @PersistenceContext
    EntityManager entityManager;

    public List<Credit> findByBankID(UUID uuid) {
        return entityManager.createQuery(
                "select c from Credit c where c.bank.ID='"+ uuid +"'", Credit.class)
                .getResultList();
    }

}
