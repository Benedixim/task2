package org.example.source.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class AuditService {

    /**
     * Logs an action performed by a player.
     *
     * @param playerId the ID of the player
     * @param action the action performed by the player
     */
    public void logAction(String playerId, String action) {
        try {
            FileWriter writer = new FileWriter("log" + playerId + ".txt", true); // создаем объект FileWriter для записи в файл "log.txt" с возможностью добавления новых строк
            writer.write("Player " + playerId + ": " + action + " " + new Date() + "\n"); // записываем строку в файл
            writer.close(); // закрываем объект FileWriter
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        System.out.println("Player " + playerId + ": " + action + " " + new Date());
    }
}
