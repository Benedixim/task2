package org.example.source.tests;


import org.example.source.models.Player;
import org.example.source.services.AuthenticationService;
import org.junit.jupiter.api.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceTest {

    @Test
    public void testLoginInvalidCredentials() {
        AuthenticationService authService = new AuthenticationService();

        String playerId = "123";
        String username = "testuser";
        String password = "testpassword";

        authService.registerPlayer(username, password);

        Player loggedInPlayer = authService.login(username, "wrongpassword");

        assertNull(loggedInPlayer);
    }
}