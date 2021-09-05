package com.example.demo.dao.interfaces;

import com.example.demo.dao.IAbstractJpaDao;
import com.example.demo.entities.Credit;

import java.util.List;
import java.util.UUID;

public interface ICreditDao extends IAbstractJpaDao<Credit, UUID> {
    List<Credit> findByBankID(UUID uuid);
}
