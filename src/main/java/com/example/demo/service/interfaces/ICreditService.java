package com.example.demo.service.interfaces;

import com.example.demo.entities.Credit;

import java.util.List;
import java.util.UUID;

public interface ICreditService {
    List<Credit> getAll();
    Credit getOne(UUID uuid);
    void save(Credit credit);
    void update(Credit credit);
    void deleteById(UUID uuid);
    void delete(Credit credit);
    List<Credit> getByBankID(UUID uuid);
}
