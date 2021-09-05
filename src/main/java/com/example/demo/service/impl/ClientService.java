package com.example.demo.service.impl;

import com.example.demo.dao.interfaces.IClientDao;
import com.example.demo.entities.Client;
import com.example.demo.service.interfaces.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService implements IClientService {


    IClientDao dao;

    @Autowired
    public ClientService(IClientDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Client> getAll() {

        List<Client> clients = dao.findAll();
        for (Client client:
             clients) {
            client.setCreditOffers(null);
        }

        return clients;
    }



    @Override
    public Client getOne(UUID uuid) {
        return dao.findOne(uuid);
    }

    @Override
    public void save(Client client) {
        dao.save(client);
    }

    @Override
    public void update(Client client) {
        dao.update(client);
    }

    @Override
    public void deleteById(UUID uuid) {
        dao.deleteById(uuid);
    }

    @Override
    public void delete(Client client) {
        dao.delete(client);
    }

    //todo
    @Override
    public Client getByPassportID(String passport) {
        return dao.findByPassportID(passport);
    }
}
