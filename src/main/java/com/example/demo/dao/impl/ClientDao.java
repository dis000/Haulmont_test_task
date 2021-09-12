package com.example.demo.dao.impl;

import com.example.demo.dao.AbstractJpaDao;
import com.example.demo.dao.interfaces.IClientDao;
import com.example.demo.entities.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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
                "select c from Client c join fetch c.creditOffers where c.ID='"+ uuid +"'", Client.class)
                .getSingleResult();
    }

    public Client findByPassportID(String passportID) {

        return entityManager.createQuery(
                "select c from Client c where c.passportID='" + passportID + "'", Client.class)
                .getSingleResult();

    }

    @Override
    @Transactional
    public void delete(Client client) {


        entityManager.flush();
        entityManager.clear();

        Query q = entityManager.createQuery("DELETE from CreditOffer i WHERE i.client.ID = :clientId");
        q.setParameter("clientId", client.getID());
        q.executeUpdate();

        client = entityManager.find(Client.class, client.getID());
        entityManager.remove(client);
    }

}
