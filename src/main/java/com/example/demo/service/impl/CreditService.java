package com.example.demo.service.impl;

import com.example.demo.dao.interfaces.ICreditDao;
import com.example.demo.entities.Bank;
import com.example.demo.entities.Credit;
import com.example.demo.service.interfaces.ICreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CreditService implements ICreditService {

    ICreditDao dao;

    @Autowired
    public CreditService(ICreditDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Credit> getAll() {
        return dao.findAll();
    }

    @Override
    public Credit getOne(UUID uuid) {
        return dao.findOne(uuid);
    }

    @Override
    public void save(Credit credit) {
        dao.save(credit);
    }

    @Override
    public void update(Bank bank, BigDecimal creditLimit, BigDecimal percentRate) {

        dao.update(new Credit(bank,creditLimit,percentRate));
    }

    @Override
    public void deleteById(UUID uuid) {
        dao.deleteById(uuid);
    }

    @Override
    public void delete(Credit credit) {
        dao.delete(credit);
    }

    @Override
    public List<Credit> getByBankID(UUID uuid) {
        return dao.findByBankID(uuid);
    }
}
