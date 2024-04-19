package com.infokey.infokey.interfaces.DAO;

import java.util.Optional;

public interface IDAO<T> {
    void save(T item);

    void update(T item, String id);

    Optional<T> findById(String id);
    
    void delete(String id);
}
