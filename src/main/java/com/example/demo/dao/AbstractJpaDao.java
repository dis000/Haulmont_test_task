package com.example.demo.dao;

import com.example.demo.entities.IEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
public abstract class AbstractJpaDao<T extends IEntity, ID extends Serializable> implements IAbstractJpaDao<T, ID>{
    private Class<T> clazz;


    @PersistenceContext
    EntityManager entityManager;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Transactional
    public T findOne(ID entityId) {
         return entityManager.find(clazz, entityId);
    }

    @Transactional
    public List<T> findAll() {
        return entityManager.createQuery(
                "select n from "+ clazz.getName() +" n", clazz)
                .getResultList();
    }

    @Transactional
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        entityManager.remove(entityManager.find(clazz, entity.getID()));

    }

    @Transactional
    public void deleteById(ID entityId) {
        entityManager.remove(entityManager.find(clazz, entityId));

    }

}
