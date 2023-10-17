package org.example.source.tests;

import org.example.source.services.AuditService;
import org.junit.jupiter.api.Test;
;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class AuditServiceTest {

    @Test
    public void testLogAction() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String playerId = "player1";
        String action = "deposit";
        Date date = new Date();

        AuditService auditService = new AuditService();
        auditService.logAction(playerId, action);

        String expectedOutput = "Player " + playerId + ": " + action + " " + date.toString() + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(), "Output does not match expected value");
    }
}