package com.infokey.infokey.DAO;

public interface DAO<T> {
    public void save(T item);
    public void update(T item);
    public T[] findAll();
    public T findById(String id);
    public void delete(String id);
}
