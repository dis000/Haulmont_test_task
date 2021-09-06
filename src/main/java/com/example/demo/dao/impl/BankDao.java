package com.example.demo.dao.impl;

import com.example.demo.dao.AbstractJpaDao;
import com.example.demo.dao.interfaces.IBankDao;
import com.example.demo.entities.Bank;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class BankDao extends AbstractJpaDao<Bank, UUID> implements IBankDao {

    public BankDao() {
        setClazz(Bank.class);
    }

    @PersistenceContext
    EntityManager entityManager;




    public Bank findBankClients(UUID uuid) {
        return entityManager.createQuery(
                "select b from Bank b join fetch b.clients where b.ID='"+ uuid +"'", Bank.class)
                .getSingleResult();
    }

    public Bank findBankCredits(UUID uuid) {
        return entityManager.createQuery(
                "select b from Bank b join fetch b.credits where b.ID='"+ uuid +"'", Bank.class)
                .getSingleResult();
    }
    public Bank findByName(String name) {
        return entityManager.createQuery("select b from Bank b where b.name ='" + name + "'",Bank.class)
                .getSingleResult();
    }
}
