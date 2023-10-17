/**
 * Класс Configs представляет собой конфигурационные настройки для подключения к базе данных.
 * Содержит поля dbHost, dbPort, dbUser, dbPass и dbName, которые соответствуют хосту, порту,
 * имени пользователя, паролю и названию базы данных соответственно.
 * По умолчанию заданы значения для локальной базы данных PostgreSQL.
 *
 * @version 1.0
 * @since 2023-10-17
 * @author Андрей Колесинский
 */
package org.example.source.configs;

public class Configs {
    protected String dbHost = "localhost";
    protected String dbPort = "5433";
    protected String dbUser = "postgres";
    protected String dbPass = "12345";
    protected String dbName = "postgres";
}
