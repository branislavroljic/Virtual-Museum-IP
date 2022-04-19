package org.unibl.etf.virtual_bank_jsp_m2.dao;

import com.google.common.hash.Hashing;
import org.unibl.etf.virtual_bank_jsp_m2.exceptions.NotFoundException;
import org.unibl.etf.virtual_bank_jsp_m2.models.BankAccount;
import org.unibl.etf.virtual_bank_jsp_m2.models.Transaction;
import org.unibl.etf.virtual_bank_jsp_m2.models.requests.BankAccountRequest;
import org.unibl.etf.virtual_bank_jsp_m2.utils.Mapper;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankAccountDAO {

    private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    private static final String SELECT_ACCOUNT = "SELECT id, balance, blocked FROM bank_account where name=? and " +
            "surname=? and card_number=? and card_type=? and expiration_date=? and pin=?";
    private static final String UPDATE_ACCOUNT_STATUS = "UPDATE bank_account SET blocked=? WHERE id=?";
    private static final String SELECT_ALL_TRANSACTIONS = "SELECT * FROM transaction WHERE bank_account_id=? order by date desc";

    public static BankAccount selectAccountByProvidedData(BankAccountRequest request) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        BankAccount bankAccount = null;
        String pinSha256 = Hashing.sha256().hashString(request.getPin(), StandardCharsets.UTF_8).toString();
        System.out.println(pinSha256);;
        Object values[] = {request.getName(), request.getSurname(), request.getCardNumber(),
                request.getCardType().toString(), request.getExpirationDate(), pinSha256};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection,
                    SELECT_ACCOUNT, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bankAccount = Mapper.from(request);
                bankAccount.setId(resultSet.getInt("id"));
                bankAccount.setBalance(resultSet.getDouble("balance"));
                bankAccount.setBlocked(resultSet.getBoolean("blocked"));
            } else throw new NotFoundException("Account not found!");
            preparedStatement.close();
        } finally {
            connectionPool.checkIn(connection);
        }
        return bankAccount;
    }

    public static boolean changeAccountStatus(BankAccount bankAccount) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;

        Object values[] = {bankAccount.getBlocked()?1:0, bankAccount.getId()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, UPDATE_ACCOUNT_STATUS, false, values);

            int res = preparedStatement.executeUpdate();
            if (res == 1) {
                return true;
            }
        } finally {
            connectionPool.checkIn(connection);
        }
        return false;
    }

    public static List<Transaction> getAllTransactions(BankAccount bankAccount) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        List<Transaction> transactions = new ArrayList<>();
        Object values[] = {bankAccount.getId()};
        try {
            connection = connectionPool.checkOut();
            PreparedStatement preparedStatement = DAOUtil.prepareStatement(connection, SELECT_ALL_TRANSACTIONS, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                transactions.add(new Transaction(resultSet.getDate("date"), resultSet.getDouble("amount"), resultSet.getString("description")));
            }
        } finally {
            connectionPool.checkIn(connection);
        }
        return transactions;
    }

}
