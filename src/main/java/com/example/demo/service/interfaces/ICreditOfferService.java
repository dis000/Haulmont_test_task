package com.example.demo.service.interfaces;

import com.example.demo.entities.CreditOffer;

import java.util.List;
import java.util.UUID;

public interface ICreditOfferService {
    List<CreditOffer> getAll();
    CreditOffer getOne(UUID uuid);
    void save(CreditOffer creditOffer);
    void update(CreditOffer creditOffer);
    void deleteById(UUID uuid);
    void delete(CreditOffer creditOffer);
    List<CreditOffer> getByCreditID(UUID uuid);
    List<CreditOffer> getByClientID(UUID uuid);
}
