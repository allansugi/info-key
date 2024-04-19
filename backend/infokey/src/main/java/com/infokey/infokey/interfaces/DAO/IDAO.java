package com.infokey.infokey.interfaces.DAO;

import java.sql.SQLException;

public interface IDAO<T> {
    void save(T item) throws SQLException;

    void update(T item) throws SQLException;

    T findById(String id) throws SQLException;
    
    void delete(String id) throws SQLException;
}
