package org.example.source.services;

import org.example.source.database.DatabaseHandler;
import org.example.source.models.Player;

public class AuthenticationService {
    public AuthenticationService() {}

    /**
     * Registers a player with the given username and password.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     */
    public void registerPlayer(String username, String password) {
        Player player = new Player(username, password);
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.signUpPlayer(player);
    }

    /**
     * Logs in a player with the given username and password.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     * @return The logged-in player.
     */
    public Player login(String username, String password) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        return databaseHandler.loginPlayer(username, password);
    }
}

