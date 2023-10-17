package org.example.source.tests;


import org.example.source.models.Account;
import org.example.source.models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void testGetPlayerId() {
        String playerId = "1";
        Player player = new Player(playerId, "testUsername", "testPassword", new Account("1"));

        assertEquals(playerId, player.getPlayerId());
    }

    @Test
    public void testGetUsername() {
        String username = "testUsername";
        Player player = new Player("1", username, "testPassword", new Account("1"));

        assertEquals(username, player.getUsername());
    }

    @Test
    public void testGetPassword() {
        String password = "testPassword";
        Player player = new Player("1", "testUsername", password, new Account("1"));

        assertEquals(password, player.getPassword());
    }

    @Test
    public void testGetAccount() {
        Account account = new Account("1");
        Player player = new Player("1", "testUsername", "testPassword", account);

        assertEquals(account, player.getAccount());
    }
}
