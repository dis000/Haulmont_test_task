package com.example.demo.dao.impl;

import com.example.demo.dao.AbstractJpaDao;
import com.example.demo.dao.interfaces.ICreditOfferDao;
import com.example.demo.entities.Client;
import com.example.demo.entities.CreditOffer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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

    @Override
    @Transactional
    public void delete(CreditOffer creditOffer) {
        entityManager.flush();
        entityManager.clear();

        Query qd = entityManager.createQuery("delete from PaymentSchedule p where p.creditOffer.ID = :creditOfferId");
        qd.setParameter("creditOfferId",creditOffer.getID());
        qd.executeUpdate();

        creditOffer = entityManager.find(CreditOffer.class, creditOffer.getID());
        entityManager.remove(creditOffer);
    }

}
