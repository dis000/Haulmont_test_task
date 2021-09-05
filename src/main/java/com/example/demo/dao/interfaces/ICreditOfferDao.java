package com.example.demo.dao.interfaces;

import com.example.demo.dao.IAbstractJpaDao;
import com.example.demo.entities.CreditOffer;

import java.util.List;
import java.util.UUID;

public interface ICreditOfferDao extends IAbstractJpaDao<CreditOffer, UUID> {
    List<CreditOffer> findByCreditID(UUID uuid);
    List<CreditOffer> findByClientID(UUID uuid);
}
