package com.example.demo.service.impl;

import com.example.demo.dao.interfaces.IBankDao;
import com.example.demo.entities.Bank;
import com.example.demo.service.interfaces.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
class BankService implements IBankService {

    IBankDao dao;

    @Autowired
    public BankService(IBankDao dao) {
        this.dao = dao;
    }

    public List<Bank> getAll() {
        return dao.findAll();
    }

    @Override
    public void save(Bank bank) {
        dao.save(bank);
    }

    @Override
    public void update(Bank bank) {
        dao.update(bank);
    }

    @Override
    public void delete(Bank bank) {
        dao.delete(bank);
    }

    @Override
    public Bank getBankClients(UUID uuid) {
        return dao.findBankClients(uuid);
    }

    @Override
    public Bank getBankCredits(UUID uuid) {
        return dao.findBankCredits(uuid);
    }

    @Override
    public Bank getById(UUID uuid) {
        return dao.findOne(uuid);
    }

    @Override
    public void deleteByID(UUID uuid) {
        dao.deleteById(uuid);
    }
}