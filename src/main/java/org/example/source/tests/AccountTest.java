package org.example.source.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.example.source.models.Account;
import org.example.source.models.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class AccountTest {
    private Account account;

    @Before
    public void setUp() {
        account = new Account("1");
    }

    @Test
    public void testGetPlayerId() {
        String playerId = account.getPlayerId();
        Assert.assertEquals("1", playerId);
    }

    @Test
    public void testGetBalance() {
        double balance = account.getBalance();
        Assert.assertEquals(0.0, balance, 0.0);
    }

    @Test
    public void testSetBalance() {
        account.setBalance(100.0);
        double balance = account.getBalance();
        Assert.assertEquals(100.0, balance, 0.0);
    }

    @Test
    public void testGetTransactionHistory() {
        List<Transaction> transactionHistory = account.getTransactionHistory();
        Assert.assertNotNull(transactionHistory);
        Assert.assertTrue(transactionHistory.isEmpty());
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction1 = new Transaction("1", "1", "credit", 50, new Date());
        account.addTransaction(transaction1);

        List<Transaction> transactionHistory = account.getTransactionHistory();
        Assert.assertEquals(1, transactionHistory.size());
        Assert.assertEquals(transaction1, transactionHistory.get(0));

        Transaction transaction2 = new Transaction("1", "1", "credit", 25, new Date());
        account.addTransaction(transaction2);

        Assert.assertEquals(2, transactionHistory.size());
        Assert.assertEquals(transaction2, transactionHistory.get(1));
    }

    @Test
    public void testCreditFundsWithValidTransaction() {
        Transaction transaction = new Transaction("1", "1", "credit", 50, new Date());
        boolean result = account.creditFunds(transaction);

        Assert.assertTrue(result);
        Assert.assertEquals(50.0, account.getBalance(), 0.0);
        Assert.assertEquals(1, account.getTransactionHistory().size());
        Assert.assertEquals(transaction, account.getTransactionHistory().get(0));
    }

    @Test
    public void testCreditFundsWithInvalidPlayerId() {
        Transaction transaction = new Transaction("2", "2", "credit", 50, new Date());
        boolean result = account.creditFunds(transaction);

        Assert.assertFalse(result);
        Assert.assertEquals(0.0, account.getBalance(), 0.0);
        Assert.assertTrue(account.getTransactionHistory().isEmpty());
    }

    @Test
    public void testCreditFundsWithNegativeAmount() {
        Transaction transaction = new Transaction("1", "1", "credit", -50.0, new Date());
        boolean result = account.creditFunds(transaction);

        Assert.assertFalse(result);
        Assert.assertEquals(0.0, account.getBalance(), 0.0);
        Assert.assertTrue(account.getTransactionHistory().isEmpty());
    }

    @Test
    public void testDebitFundsWithValidTransaction() {
        account.setBalance(100.0);

        Transaction transaction = new Transaction("1", "1", "debit", 50.0, new Date());
        boolean result = account.debitFunds(transaction);

        Assert.assertTrue(result);
        Assert.assertEquals(50.0, account.getBalance(), 0.0);
        Assert.assertEquals(1, account.getTransactionHistory().size());
        Assert.assertEquals(transaction, account.getTransactionHistory().get(0));
    }

    @Test
    public void testDebitFundsWithInvalidPlayerId() {
        account.setBalance(100.0);

        Transaction transaction = new Transaction("2", "2", "debit", 50, new Date());
        boolean result = account.debitFunds(transaction);

        Assert.assertFalse(result);
        Assert.assertEquals(100.0, account.getBalance(), 0.0);
        Assert.assertTrue(account.getTransactionHistory().isEmpty());
    }

}


