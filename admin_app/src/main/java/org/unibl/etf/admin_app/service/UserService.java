package org.unibl.etf.admin_app.service;

import org.unibl.etf.admin_app.beans.UserBean;
import org.unibl.etf.admin_app.beans.enums.UserStatus;
import org.unibl.etf.admin_app.dao.UserDAO;
import org.unibl.etf.admin_app.util.PasswordUtil;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserService implements Serializable {

    public List<UserBean> getUsers() {
        try {
            return UserDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public UserBean loginUser(String token) {
        try {
            UserBean user = UserDAO.selectAdminByToken(token);
            if (user != null) {
                UserDAO.invalidateOtp(user.getId());
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserStatus(String id) {
        try {
            UserBean reqUser = UserDAO.getById(id);
            if (UserDAO.updateUserStatus(id, reqUser.getStatus().equals(UserStatus.ACTIVE) ? UserStatus.BLOCKED :
                    UserStatus.ACTIVE)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addUser(UserBean user){
        try {
            return UserDAO.add(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editUser(UserBean user) {

        try {
            return (UserDAO.updateUser(user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String id) {
        try {
            return (UserDAO.deleteById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean resetPassword(String id) {
        try {
            String randomPassword = PasswordUtil.generateRandomPassword();
            UserDAO.updatePassword(id, PasswordUtil.getPasswordBCryptHash(randomPassword));
            MailService.sendMail(id, "Admin has reset your password. The new password is: " + randomPassword);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
