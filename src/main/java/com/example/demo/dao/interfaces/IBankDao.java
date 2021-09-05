package com.example.demo.dao.interfaces;

import com.example.demo.dao.IAbstractJpaDao;
import com.example.demo.entities.Bank;

import java.util.UUID;

public interface IBankDao extends IAbstractJpaDao<Bank, UUID> {


    Bank findBankClients(UUID uuid);
    Bank findBankCredits(UUID uuid);
}
