package com.infokey.infokey.interfaces.DAO;

import java.util.Optional;

public interface IDAO<T> {
    int save(T item);

    int update(T item, String id);

    Optional<T> findById(String id);
    
    int delete(String id);
}
