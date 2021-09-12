package com.example.demo.dao.interfaces;

import com.example.demo.dao.IAbstractJpaDao;
import com.example.demo.entities.Client;

import java.util.UUID;

public interface IClientDao extends IAbstractJpaDao<Client, UUID> {
    Client findCreditOffers(UUID uuid);
    Client findByPassportID(String passportID);
    void delete(Client client);
}
