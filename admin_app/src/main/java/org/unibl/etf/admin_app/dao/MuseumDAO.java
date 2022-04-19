package org.unibl.etf.admin_app.dao;

import org.unibl.etf.admin_app.beans.MuseumBean;
import org.unibl.etf.admin_app.beans.UserBean;
import org.unibl.etf.admin_app.beans.enums.UserRole;
import org.unibl.etf.admin_app.beans.enums.UserStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MuseumDAO {

    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private static final String SELECT_ALL = "SELECT * FROM museum;";
    private static final String UPDATE_MUSEUM = "UPDATE museum set name=?, address=?, tel=?, city=?, contry=?, geolat=?, geolng=?, type=? where id=?";
    private static final String ADD_MUSEUM = "INSERT INTO museum(name, address, tel, city, contry, geolat, geolng, type) values (?,?,?,?,?,?,?,?)";
    private static final String DELETE_MUSEUM = "DELETE FROM museum WHERE id=?";


    public static List<MuseumBean> selectAll() throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        List<MuseumBean> museums = new ArrayList<>();

        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                museums.add(new MuseumBean(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("tel"), resultSet.getString("city"), resultSet.getString("contry"), resultSet.getDouble("geolat"), resultSet.getDouble("geolng"), resultSet.getString("type")));
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return museums;
    }

    public static boolean updateMuseum(MuseumBean museum) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {museum.getName(), museum.getAddress(), museum.getTel(), museum.getCity(), museum.getCountry(), museum.getGeolat(), museum.getGeolat(), museum.getType(), museum.getId()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, UPDATE_MUSEUM, false, values);

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

    public static boolean add(MuseumBean museum) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        boolean result = false;
        Object values[] = {museum.getName(), museum.getAddress(), museum.getTel(), museum.getCity(), museum.getCountry(), museum.getGeolat(), museum.getGeolat(), museum.getType()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, ADD_MUSEUM, true, values);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (preparedStatement.getUpdateCount() > 0)
                result = true;
            if (resultSet.next())/**/
                System.out.println("Bio sam u add id");
            museum.setId(resultSet.getString(1));
            preparedStatement.close();

            System.out.println("Dodat muzej: " + museum.getName());
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }

    public static boolean deleteById(String id) throws SQLException {
        Connection connection = null;
        Object values[] = {id};
        boolean result = false;
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    DELETE_MUSEUM, false, values);
            result = preparedStatement.executeUpdate() == 1;
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }
}
