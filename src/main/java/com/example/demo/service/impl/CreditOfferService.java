package com.example.demo.service.impl;

import com.example.demo.dao.interfaces.ICreditOfferDao;
import com.example.demo.entities.CreditOffer;
import com.example.demo.service.interfaces.ICreditOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CreditOfferService implements ICreditOfferService {

    ICreditOfferDao dao;

    @Autowired
    public CreditOfferService(ICreditOfferDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CreditOffer> getAll() {
        return dao.findAll();
    }

    @Override
    public CreditOffer getOne(UUID uuid) {
        return dao.findOne(uuid);
    }

    @Override
    public void save(CreditOffer creditOffer) {
        dao.save(creditOffer);
    }

    @Override
    public void update(CreditOffer creditOffer) {
        dao.update(creditOffer);
    }

    @Override
    public void deleteById(UUID uuid) {
        dao.deleteById(uuid);
    }

    @Override
    public void delete(CreditOffer creditOffer) {
        dao.delete(creditOffer);
    }

    @Override
    public List<CreditOffer> getByCreditID(UUID uuid) {
        return dao.findByCreditID(uuid);
    }

    @Override
    public List<CreditOffer> getByClientID(UUID uuid) {
        return dao.findByClientID(uuid);
    }
}
