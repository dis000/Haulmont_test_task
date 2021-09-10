package com.example.demo.service.interfaces;

import com.example.demo.entities.Bank;
import com.example.demo.entities.Credit;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ICreditService {
    List<Credit> getAll();
    Credit getOne(UUID uuid);
    void save(Credit credit);
    void update(Bank bank, BigDecimal creditLimit, BigDecimal percentRate);
    void deleteById(UUID uuid);
    void delete(Credit credit);
    List<Credit> getByBankID(UUID uuid);
}
