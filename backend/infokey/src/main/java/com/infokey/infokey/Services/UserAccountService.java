package com.infokey.infokey.Services;

import com.infokey.infokey.DAO.UserDAO;
import com.infokey.infokey.DTO.UserAccount;
import com.infokey.infokey.Exceptions.UserAccount.PasswordRequirementException;
import com.infokey.infokey.Exceptions.UserAccount.UnauthorizedCredentialException;
import com.infokey.infokey.Exceptions.UserAccount.UserAccountNotFoundException;
import com.infokey.infokey.Form.LoginForm;
import com.infokey.infokey.Form.RegisterForm;
import com.infokey.infokey.Form.UpdateUserPasswordForm;
import com.infokey.infokey.Mapper.UserAccountMapper;
import com.infokey.infokey.Response.Response;
import com.infokey.infokey.Template.UserAccountPasswordRequirementValidator;
import com.infokey.infokey.Util.JWTUtil;
import com.infokey.infokey.ViewModel.UserInfo;
import com.infokey.infokey.interfaces.Service.IUserAccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        // username input can be their username or email
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

    @Override
    public Response<UserInfo> getUserInfo(String token) {
        String userId = jwt.verifyToken(token);
        Optional<UserAccount> account = dao.findById(userId);

        if (account.isEmpty()) {
            throw new UserAccountNotFoundException("no user found");
        }

        UserAccount getAccount = account.get();
        UserInfo info = mapper.toUserInfo(getAccount);
        return new Response<>(true, info);
    }

    @Override
    public Response<String> updatePassword(String token, UpdateUserPasswordForm form, HttpServletResponse res) {
        String id = jwt.verifyToken(token);
        Optional<UserAccount> userAccountOptional = dao.findById(id);
        if (userAccountOptional.isEmpty()) {
            throw new UserAccountNotFoundException("account not found to be updated");
        }

        UserAccount user = userAccountOptional.get();
        if (!user.getPassword().equals(form.getUserInputMasterPassword())) {
            throw new UnauthorizedCredentialException("wrong password credentials");
        }

        int updatedRow = dao.updatePassword(form.getNewPassword(), id);
        if (updatedRow == 0) {
            return new Response<>(false, "fail to update password");
        }

        // remove cookie
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return new Response<>(true, "password updated");
    }

    @Override
    public Response<String> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return new Response<>(true, "user log out");
    }
}
