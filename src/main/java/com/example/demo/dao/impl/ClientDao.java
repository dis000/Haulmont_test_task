package com.example.demo.dao.impl;

import com.example.demo.dao.AbstractJpaDao;
import com.example.demo.dao.interfaces.IClientDao;
import com.example.demo.entities.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class ClientDao extends AbstractJpaDao<Client, UUID> implements IClientDao {

    public ClientDao() {
        setClazz(Client.class);
    }

    @PersistenceContext
    EntityManager entityManager;


    public Client findCreditOffers(UUID uuid) {
        return entityManager.createQuery(
                "select c from Client c left join c.creditOffers where c.ID='"+ uuid +"'", Client.class)
                .getSingleResult();
    }

    public Client findByPassportID(String passportID) {

        return entityManager.createQuery(
                "select c from Client c where c.passportID='" + passportID + "'", Client.class)
                .getSingleResult();

    }

}
