package org.unibl.etf.admin_app.dao;

import org.unibl.etf.admin_app.beans.UserBean;
import org.unibl.etf.admin_app.beans.enums.UserRole;
import org.unibl.etf.admin_app.beans.enums.UserStatus;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private static final String SELECT_ALL = "SELECT id, name, surname, username, email, role, status FROM user;";
    private static final String SELECT_BY_ID = "SELECT * FROM user where id=?;";
    private static final String SELECT_ADMIN_BY_TOKEN = "SELECT id, username FROM user WHERE role='ADMIN' and token=?";
    private static final String UPDATE_USER_STATUS = "UPDATE user SET status=?, logged_in=0 WHERE id=?";
    private static final String ADD_USER = "INSERT INTO user (username, password, email, name, surname, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET username=? , password=? , email=? , name=? , surname=? , role=? WHERE id=?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id=?";
    private static final String COUNT_TOTAL = "SELECT count(*) as count FROM user";
    private static final String COUNT_LOGGED_IN = "SELECT count(*) as count FROM user WHERE logged_in = true";
    private  static final String COUNT_TOTAL_REGISTERED = "SELECT count(*) as count FROM user WHERE status = 'ACTIVE'";
    private static final String UPDATE_PASSWORD = "UPDATE user SET password=? WHERE id=?";
    private static final String INVALIDATE_OTP = "UPDATE user SET token='' WHERE id=?";
    private static final String SELECT_USER_MAIL_BY_ID = "SELECT email FROM user WHERE id=?";



    public static List<UserBean> selectAll() throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        List<UserBean> users = new ArrayList<>();

        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                UserStatus status = UserStatus.valueOf(resultSet.getString("status"));
                users.add(new UserBean(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("username"),
                     null, resultSet.getString("email"), role, status));
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return users;
    }


    public static UserBean getById(String Id) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {Id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SELECT_BY_ID, false, values);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserRole role = UserRole.valueOf(resultSet.getString("role"));
                UserStatus status = UserStatus.valueOf(resultSet.getString("status"));
                return new UserBean(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("username"),
                        null, resultSet.getString("email"), role, status);
            }
        } finally {
            connectionPool.checkIn(connection);
        }
        return null;
    }

    public static UserBean selectAdminByToken(String token) throws SQLException {

        UserBean admin = null;
        Connection connection = null;
        ResultSet resultSet = null;
        Object values[] = {token};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    SELECT_ADMIN_BY_TOKEN, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admin = new UserBean();
                admin.setId(resultSet.getString("id"));
                admin.setUsername(resultSet.getString("username"));
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return admin;
    }

    public static boolean updateUserStatus(String id, UserStatus status) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {status.toString(), id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, UPDATE_USER_STATUS, false, values);

            int res = preparedStatement.executeUpdate();
            if (res == 1) {
                return true;
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return false;
    }

    //private static final String ADD_USER = "INSERT INTO user (username, password, email, name, surname, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static boolean add(UserBean user) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        boolean result = false;
        Object values[] = {user.getUsername(), user.getPassword(), user.getEmail(), user.getName(), user.getSurname(), user.getRole().toString(), user.getStatus().toString()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, ADD_USER, true, values);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (preparedStatement.getUpdateCount() > 0)
                result = true;
            if (resultSet.next())/**/
            user.setId(resultSet.getString(1));
            preparedStatement.close();

        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

//  private static final String UPDATE_USER = "UPDATE user SET username=? , password=? , email=? , name=? , surname=? , role=? WHERE id=?";
    public static boolean updateUser(UserBean user) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {user.getUsername(), user.getPassword(), user.getEmail(), user.getName(), user.getSurname(), user.getRole().toString(),  user.getId()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, UPDATE_USER, false, values);

            int res = preparedStatement.executeUpdate();
            if (res == 1) {
                return true;
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return false;
    }


    public static boolean deleteById(String id) throws SQLException {
        Connection connection = null;
        Object values[] = {id};
        boolean result = false;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    DELETE_USER, false, values);
            result = preparedStatement.executeUpdate() == 1;
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

    public static Integer countTotalUsers(){
        Connection connection = null;
        ResultSet rs = null;
        int count = 0;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    COUNT_TOTAL, false);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                count = rs.getInt("count");
            }
            preparedStatement.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return count;
    }

    public static Integer countLoggedInUsers() throws SQLException {
        Connection connection = null;
        ResultSet rs = null;
        int count = 0;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    COUNT_LOGGED_IN, false);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                count = rs.getInt("count");
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return count;
    }

    public static Integer countRegisteredUsers() throws SQLException {
        Connection connection = null;
        ResultSet rs = null;
        int count = 0;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    COUNT_TOTAL_REGISTERED, false);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                count = rs.getInt("count");
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return count;
    }

    public static boolean updatePassword(String id, String password) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {password, id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, UPDATE_PASSWORD, false, values);

            int res = preparedStatement.executeUpdate();
            if (res == 1) {
                return true;
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return false;
    }

    public static boolean invalidateOtp(String id) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, INVALIDATE_OTP, false, values);

            int res = preparedStatement.executeUpdate();
            if (res == 1) {
                return true;
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return false;
    }

    public static String getUserMailById(String id){
        Connection connection = null;
        ResultSet rs = null;
        String email = null;
        Object values[] = {id};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    SELECT_USER_MAIL_BY_ID, false, values);
            rs = preparedStatement.executeQuery();
            while (rs.next()){
                email = rs.getString("email");
            }
            preparedStatement.close();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionPool.checkIn(connection);
        }
        return email;
    }
}
