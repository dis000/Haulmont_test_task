package com.example.demo.service.interfaces;

import com.example.demo.entities.Bank;

import java.util.List;
import java.util.UUID;

public interface IBankService {
    List<Bank> getAll();
    void save(Bank bank);
    void update(Bank bank);
    void delete(Bank bank);
    Bank getBankClients(UUID uuid);
    Bank getBankCredits(UUID uuid);
    Bank getById(UUID uuid);
    void deleteByID(UUID uuid);
}
