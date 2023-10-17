package org.example.source.tests;


import org.example.source.models.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    public void testTransactionConstructor() {
        String transactionId = "123";
        String playerId = "456";
        String type = "deposit";
        double amount = 100.0;
        Date date = new Date();

        Transaction transaction = new Transaction(transactionId, playerId, type, amount, date);

        assertEquals(transactionId, transaction.getTransactionId());
        assertEquals(playerId, transaction.getPlayerId());
        assertEquals(type, transaction.getType());
        assertEquals(amount, transaction.getAmount(), 0.0);
        assertEquals(date, transaction.getDate());
    }

    @Test
    public void testToString() {
        String transactionId = "123";
        String playerId = "456";
        String type = "deposit";
        double amount = 100.0;
        Date date = new Date();

        Transaction transaction = new Transaction(transactionId, playerId, type, amount, date);

        String expectedString = "Transaction{transactionId='123', playerId='456', type='deposit', amount=100.0, date=" + date.toString() + "}";

        assertEquals(expectedString, transaction.toString());
    }
}