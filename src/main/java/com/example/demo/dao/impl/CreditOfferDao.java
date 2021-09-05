package com.example.demo.dao.impl;

import com.example.demo.dao.AbstractJpaDao;
import com.example.demo.dao.interfaces.ICreditOfferDao;
import com.example.demo.entities.CreditOffer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
public class CreditOfferDao extends AbstractJpaDao<CreditOffer, UUID> implements ICreditOfferDao {

    public CreditOfferDao() {
        setClazz(CreditOffer.class);
    }

    @PersistenceContext
    EntityManager entityManager;


    public List<CreditOffer> findByCreditID(UUID uuid) {
        return entityManager.createQuery(
                "select c from CreditOffer c where c.credit.ID='" + uuid + "'", CreditOffer.class)
                .getResultList();
    }

    public List<CreditOffer> findByClientID(UUID uuid) {
        return entityManager.createQuery(
                "select c from CreditOffer c where c.client.ID='" + uuid + "'",CreditOffer.class)
                .getResultList();
    }

}
