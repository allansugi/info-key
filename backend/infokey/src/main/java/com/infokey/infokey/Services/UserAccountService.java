package com.infokey.infokey.Services;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.Exceptions.IllegalRegisterException;
import com.infokey.infokey.Exceptions.LoginNotFoundException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Mapper.UserAccountMapper;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Template.UserAccountPasswordRequirementValidator;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.interfaces.Service.IUserAccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService implements IUserAccountService {

    private final UserDAO dao;
    private final JWTUtil jwt;
    private final UserAccountMapper mapper;
    public UserAccountService(UserDAO dao, JWTUtil jwt, UserAccountMapper mapper) {
        this.dao = dao;
        this.jwt = jwt;
        this.mapper = mapper;
    }

    @Override
    public Response<String> addUser(RegisterForm form) throws IllegalRegisterException {
        String password = form.getPassword();
        if (!UserAccountPasswordRequirementValidator.isValid(password)) {
            throw new IllegalRegisterException("Password does not meet the requirement");
        } else {
            UserAccount account = mapper.toDTO(form);
            dao.save(account);
            return new Response<>(true, "Registration successful");
        }
    }

    @Override
    public Response<String> authenticate(LoginForm form) throws LoginNotFoundException {
        List<UserAccount> users = this.dao.findAll();
        String valId = form.getUsername();
        List<UserAccount> validUser = users.stream()
                .filter(u -> (u.getEmail().equals(valId) || u.getUsername().equals(valId)))
                .toList();
        if (!validUser.isEmpty() && form.getPassword().equals(validUser.get(0).getPassword())) {
            return new Response<>(true, jwt.createToken(validUser.get(0).getId()));
        } else {
            throw new LoginNotFoundException("Invalid credentials");
        }
    }

    @Override
    public Response<String> updateUser(UserAccount account) {
        return null;
    }

    @Override
    public Response<String> deleteUser(int id) {
        return null;
    }
    
}
