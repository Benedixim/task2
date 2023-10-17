/**
 * Класс для работы с базой данных.
 * Наследует класс Configs.
 *
 * @version 1.0
 * @since 2023-10-17
 * @author Андрей Колесинский
 */
package org.example.source.database;


import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import org.example.source.configs.Configs;
import org.example.source.models.Player;
import org.example.source.models.Account;
import org.example.source.models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {


    /**
     * Retrieves a JDBC connection to the PostgreSQL database.
     *
     * @return a JdbcConnection object representing the database connection
     * @throws RuntimeException if an error occurs while establishing the connection
     */
    public JdbcConnection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + dbHost  + ":"
                            + dbPort + "/" + dbName,
                    dbUser,
                    dbPass
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new JdbcConnection(connection);

    }

    /**
     * Sign up a player by inserting their information into the player table with a starting balance of 0.
     * @param player The player object containing the username and password.
     */
    public void signUpPlayer(Player player){//сделать добавление счета accoutn  в таблицу с балансом 0
        String insert = "INSERT INTO " + Const.SCHEMA + "." + Const.PLAYER_TABLE + "(" + Const.PLAYER_ID + "," +
                Const.PLAYER_NAME + "," + Const.PLAYER_PASSWORD + ")" + "VALUES(?,?,?)";
        try {
            PreparedStatement prSt = null;
            prSt = getConnection().prepareStatement(insert);

            int id = Integer.parseInt(getPlayerId().toString());

            prSt.setString(1, String.valueOf(id));
            prSt.setString(2, player.getUsername());
            prSt.setString(3, player.getPassword());


            prSt.executeUpdate();

            setAccount(String.valueOf(id));
        } catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the next available player ID from the database.
     *
     * @return The next available player ID
     * @throws RuntimeException if there is an error executing the database query
     */
    public Long getPlayerId() {
        String query = "SELECT nextval('" + Const.SCHEMA + "." + Const.PLAYER_SEQUENCE + "')";
        Long playerId = null;

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                playerId = rs.getLong(1);
            }
        } catch (SQLException e ) {
            throw new RuntimeException(e);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        return playerId;
    }

    /**
     * Logs in a player with the given username and password.
     * @param username the username of the player
     * @param password the password of the player
     * @return the logged in player
     */
    public Player loginPlayer(String username, String password) {
        String query = "SELECT * FROM " + Const.SCHEMA + "." + Const.PLAYER_TABLE + " WHERE " + Const.PLAYER_NAME + "=? AND " + Const.PLAYER_PASSWORD + "=?";
        Player player = null;

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                player = new Player(rs.getString(1), rs.getString(2), rs.getString(3), getAccount(rs.getString(1)));
            }
        } catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
        return player;
    }

    public void setAccount(String player_id){
        String insert = "INSERT INTO " + Const.SCHEMA + "." + Const.ACCOUNT_TABLE + "(" + Const.ACCOUNT_PLAYER_ID + "," + Const.ACCOUNT_BALANCE + ")" + "VALUES(?,0)";
        try {
            PreparedStatement prSt = null;
            prSt = getConnection().prepareStatement(insert);

            prSt.setString(1, player_id);

            prSt.executeUpdate();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Retrieve an Account object for a given player ID.
     *
     * @param player_id The ID of the player.
     * @return The Account object associated with the player ID, or null if not found.
     * @throws RuntimeException If there is an error executing the database query.
     */
    public Account getAccount(String player_id) {
        String query = "SELECT * FROM " + Const.SCHEMA + "." + Const.ACCOUNT_TABLE + " WHERE " + Const.ACCOUNT_PLAYER_ID + "=?";
        Account account = null;

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, player_id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                account = new Account(rs.getString(1), rs.getBigDecimal(2));
            }
        } catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
        return account;

    }

    /**
     * Saves a transaction to the database.
     *
     * @param transaction The transaction to be saved.
     */
    public void saveTransaction(Transaction transaction) {
        String insert = "INSERT INTO " + Const.SCHEMA + "." + Const.TRANSACTION_TABLE +  "(" + Const.TRANSACTION_ID + "," +
                Const.TRANSACTION_PLAYER_ID + "," + Const.TRANSACTION_TYPE + "," + Const.TRANSACTION_AMOUNT + "," + Const.TRANSACTION_DATE + ")" + "VALUES(?,?,?,?,now())";
        try {
            PreparedStatement prSt = null;
            prSt = getConnection().prepareStatement(insert);

            prSt.setString(1, getTransactionId());
            prSt.setString(2, transaction.getPlayerId());
            prSt.setString(3, transaction.getType());
            prSt.setDouble(4, transaction.getAmount());



            prSt.executeUpdate();
        } catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }    }

    /**
     * Retrieves the next transaction ID from the database.
     *
     * @return The transaction ID as a string.
     * @throws RuntimeException if there is an error retrieving the transaction ID.
     */
    private String getTransactionId() {
        String query = "SELECT nextval('" + Const.SCHEMA + "." + Const.TRANSACTION_SEQUENCE + "')";
        String transactionId = null;

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                transactionId = rs.getString(1);
            }

        }
        catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
        return transactionId;
    }

    /**
     * Update the balance of an account.
     *
     * @param account The account to update.
     */
    public void updateBalance(Account account) {
        String query = "UPDATE " + Const.SCHEMA + "." + Const.ACCOUNT_TABLE + " SET " + Const.ACCOUNT_BALANCE + "=? WHERE " + Const.ACCOUNT_PLAYER_ID + "=?";
        try {
            PreparedStatement prSt = null;
            prSt = getConnection().prepareStatement(query);

            prSt.setDouble(1, account.getBalance());
            prSt.setString(2, account.getPlayerId());
            prSt.executeUpdate();
        }
        catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of transactions for a given player ID.
     *
     * @param playerId the ID of the player
     * @return a list of transactions
     */
    public List<Transaction> getTransactions(String playerId) {
        String query = "SELECT * FROM " + Const.SCHEMA + "." + Const.TRANSACTION_TABLE + " WHERE " + Const.TRANSACTION_PLAYER_ID + "=?";
        List<Transaction> transactions;

        try {
            PreparedStatement prSt = null;
            prSt = getConnection().prepareStatement(query);
            prSt.setString(1, playerId);
            ResultSet rs = prSt.executeQuery();
           transactions = new ArrayList<>();
            while (rs.next()) {
                transactions.add(new Transaction(rs.getString(1), rs.getString(2), rs.getString(3),  rs.getDouble(4), rs.getDate(5)));
            }
        }
        catch (SQLException | DatabaseException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }
}
