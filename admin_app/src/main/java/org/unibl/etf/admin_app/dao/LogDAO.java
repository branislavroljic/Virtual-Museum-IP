package org.unibl.etf.admin_app.dao;


import org.unibl.etf.admin_app.beans.LogBean;
import org.unibl.etf.admin_app.beans.ReportBean;
import org.unibl.etf.admin_app.beans.VirtualVisitBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private static final String SELECT_ALL = "SELECT * from log ORDER BY date_time DESC";
    private static final String SELECT_LOGINS_BY_HOUR_FROM_LAST_24_HOURS = "SELECT date(date_time) as 'date', hour(date_time) as 'hour', count(*) as 'count' FROM log l WHERE l.type = 'LOGIN' and date_time > DATE_SUB(NOW(), INTERVAL 24 HOUR) group by hour(date_time), date(date_time) ";

    public static List<LogBean> selectAll() throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        List<LogBean> logs = new ArrayList<>();

        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SELECT_ALL, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               logs.add(new LogBean(resultSet.getString("type"), resultSet.getString("message"), resultSet.getString("date_time")));
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return logs;
    }

    public static List<ReportBean> selectLoginsByHourFromLast24Hour() throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        List<ReportBean> reports = new ArrayList<>();

        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SELECT_LOGINS_BY_HOUR_FROM_LAST_24_HOURS, false);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reports.add(new ReportBean(resultSet.getString("date"), resultSet.getString("hour"), resultSet.getInt("count")));
            }
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return reports;
    }
}
