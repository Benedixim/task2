package org.example.source.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.source.database.DatabaseHandler;

public class LiquibaseMain {

    /**
     * This method performs database migrations using Liquibase.
     */
    public static void main(String[] args) {

        DatabaseHandler databaseHandler = new DatabaseHandler();
        Database database = null;

        try {
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseHandler.getConnection());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        Liquibase liquibase = new Liquibase("db/changelog/003-create-transaction-table.xml", new ClassLoaderResourceAccessor(), database);
        try {
            liquibase.update("");
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Миграции успешно выполнены!");

    }
}