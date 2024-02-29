package com.infokey.infokey.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.infokey.infokey.Model.UserAccount;
import com.infokey.infokey.interfaces.DAO.IDAO;

public class UserDAO implements IDAO<UserAccount> {

    @Autowired
    public UserDAO() {
        
    }

    @Override
    public void save(UserAccount item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(UserAccount item) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<UserAccount> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public UserAccount findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
