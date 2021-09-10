package com.example.demo.service.impl;

import com.example.demo.dao.interfaces.IClientDao;
import com.example.demo.entities.Bank;
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
    public void save(String name, String phone, String passportID, Bank bank) {
        dao.update(new Client(name, phone, passportID, bank));
    }

    @Override
    public void update(Client clientEdit) {

        Client client = dao.findOne(clientEdit.getID());

        client.setName(clientEdit.getName());
        client.setPassportID(clientEdit.getPassportID());
        client.setPhone(clientEdit.getPhone());
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
