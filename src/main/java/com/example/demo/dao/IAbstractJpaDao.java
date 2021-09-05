package com.example.demo.dao;


import com.example.demo.entities.IEntity;

import java.io.Serializable;
import java.util.List;

public interface IAbstractJpaDao<T extends IEntity, ID extends Serializable> {

    void setClazz(Class<T> clazz);

    T findOne(final ID id);

    List<T> findAll();

    void save(final T entity);

    void update(final T entity);

    void delete(final T entity);

    void deleteById(final ID entityId);


}