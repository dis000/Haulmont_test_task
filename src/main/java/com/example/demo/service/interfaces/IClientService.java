package com.example.demo.service.interfaces;

import com.example.demo.entities.Bank;
import com.example.demo.entities.Client;

import java.util.List;
import java.util.UUID;

public interface IClientService {

    List<Client> getAll();
    Client getOne(UUID uuid);
    void save(Client client);
    void update(Client client);
    void deleteById(UUID uuid);
    void delete(Client client);
    Client getByPassportID(String passport);
}
