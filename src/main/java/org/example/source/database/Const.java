/**
 * Класс "Const" содержит константы для названий таблиц и столбцов в базе данных.
 *
 * @version 1.0
 * @since 2023-10-17
 * @author Андрей Колесинский
 */

package org.example.source.database;

public class Const {

    /**
     * Название схемы
     */
    public static final String SCHEMA = "schema";


    /**
     * Название таблицы "player".
     */
    public static final String PLAYER_TABLE = "player";

    public static final String PLAYER_ID = "player_id";
    public static final String PLAYER_NAME = "username";
    public static final String PLAYER_PASSWORD = "password";


    /**
     * Название sequence для таблицы "player".
     */
    public static final String PLAYER_SEQUENCE = "player_sequence";

    /**
     * Название таблицы "account".
     */
    public static final String ACCOUNT_TABLE = "account";

    public static final String ACCOUNT_PLAYER_ID = "account_player_id";

    public static final String ACCOUNT_BALANCE = "balance";


    /**
     * Название таблицы "transaction".
     */
    public static final String TRANSACTION_TABLE = "transaction";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String TRANSACTION_PLAYER_ID = "transaction_player_id";
    public static final String TRANSACTION_TYPE = "type";
    public static final String TRANSACTION_AMOUNT = "amount";
    public static final String TRANSACTION_DATE = "date";

    /**
     * Название sequence для таблицы "transaction".
     */
    public static final String TRANSACTION_SEQUENCE = "player_sequence";
}
