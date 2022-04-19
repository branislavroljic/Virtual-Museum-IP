package org.unibl.etf.admin_app.dao;

import org.unibl.etf.admin_app.beans.MuseumBean;
import org.unibl.etf.admin_app.beans.UserBean;
import org.unibl.etf.admin_app.beans.VirtualVisitBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VirtualVisitDAO {

    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private static final String SELECT_ALL_BY_MUSEUM_ID = "SELECT * from virtual_visit where museum_id=?";
    private static final String UPDATE_VISIT = "UPDATE virtual_visit set date=?, start_time=?, duration=? where id=?";
    private static final String ADD_VISIT = "INSERT INTO virtual_visit (date, start_time, duration, price, museum_id) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_VISIT = "DELETE FROM virtual_visit where id=?";


    public static List<VirtualVisitBean> selectAll(String museumId) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        List<VirtualVisitBean> visits = new ArrayList<>();

        Object values[] = {museumId};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SELECT_ALL_BY_MUSEUM_ID, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                visits.add(new VirtualVisitBean(resultSet.getString("id"), resultSet.getDate("date"), resultSet.getTime("start_time"), resultSet.getInt("duration"), resultSet.getString("museum_id"), resultSet.getDouble("price")));
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return visits;
    }

    public static boolean updateVirtualVisit(VirtualVisitBean virtualVisit) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {virtualVisit.getDate(), virtualVisit.getStartTime(), virtualVisit.getDuration(), virtualVisit.getId()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, UPDATE_VISIT, false, values);

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

    public static boolean add(VirtualVisitBean visit) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        boolean result = false;
        Object values[] = {visit.getDate(), visit.getStartTime(), visit.getDuration(), visit.getPrice(), visit.getMuseumId()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, ADD_VISIT, true, values);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (preparedStatement.getUpdateCount() > 0) {
                result = true;
                if (resultSet.next()) {
                    visit.setId(resultSet.getString(1));
                } else {
                    System.out.println("NEMA ID");
                }
            }

            preparedStatement.close();
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
                    DELETE_VISIT, false, values);
            result = preparedStatement.executeUpdate() == 1;
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return result;
    }
}
