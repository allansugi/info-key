package com.infokey.infokey.Services;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.Exceptions.UserAccount.PasswordRequirementException;
import com.infokey.infokey.Exceptions.UserAccount.UnauthorizedCredentialException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Mapper.UserAccountMapper;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Template.UserAccountPasswordRequirementValidator;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.interfaces.Service.IUserAccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public Response<String> addUser(RegisterForm form) throws PasswordRequirementException {
        String password = form.getPassword();
        if (!UserAccountPasswordRequirementValidator.isValid(password)) {
            throw new PasswordRequirementException("password not meet the requirement");
        } else {
            UserAccount account = mapper.toDTO(form);
            dao.save(account);
            return new Response<>(true, "Registration successful");
        }
    }

    @Override
    public Response<String> authenticate(LoginForm form, HttpServletResponse res) throws UnauthorizedCredentialException {
        List<UserAccount> users = this.dao.findAll();
        String valId = form.getUsername();
        List<UserAccount> validUser = users.stream()
                .filter(u -> (u.getEmail().equals(valId) || u.getUsername().equals(valId)))
                .toList();
        if (!validUser.isEmpty() && form.getPassword().equals(validUser.get(0).getPassword())) {
            Cookie cookie = new Cookie("token", jwt.createToken(validUser.get(0).getId()));
            res.addCookie(cookie);
            return new Response<>(true, "success, welcome " + validUser.get(0).getUsername());
        } else {
            throw new UnauthorizedCredentialException("not matching user account");
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
