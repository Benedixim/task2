package org.example.source.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String playerId;
    private double balance;
    private final List<Transaction> transactionHistory;

    public Account(String playerId) {
        this.playerId = playerId;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public Account(String playerId, BigDecimal balance) {
        this.playerId = playerId;
        this.balance = balance.doubleValue();
        transactionHistory = new ArrayList<>();
    }

    public String getPlayerId() {
        return playerId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public boolean creditFunds(Transaction transaction) {
        // Проверяем, что playerId из транзакции совпадает с playerId аккаунта
        if (!transaction.getPlayerId().equals(this.playerId)) {
            return false;
        }

        double amount = transaction.getAmount();
        // Проверяем, что сумма транзакции положительная
        if (amount <= 0) {
            return false;
        }

        // Увеличиваем баланс аккаунта на сумму транзакции
        this.balance += amount;

        // Добавляем транзакцию в историю транзакций
        this.addTransaction(transaction);

        return true; // Возвращаем true в случае успешного зачисления средств
    }

    private boolean checkContain(String transactionId) {
        for (Transaction transaction : transactionHistory) {
            if (transaction.getTransactionId().equals(transactionId)) return true;
        }
        return false;
    }

    public boolean debitFunds(Transaction transaction) {
        // Проверяем, что playerId из транзакции совпадает с playerId аккаунта
        if (!transaction.getPlayerId().equals(this.playerId)) {
            return false;
        }

        double amount = transaction.getAmount();
        // Проверяем, что сумма транзакции положительная
        if (amount <= 0) {
            return false;
        }

        // Проверяем, что на балансе аккаунта достаточно средств для списания
        if (this.balance < amount) {
            return false; // В случае недостаточного баланса, возвращаем false
        }

        // Уменьшаем баланс аккаунта на сумму транзакции
        this.balance -= amount;

        // Добавляем транзакцию в историю транзакций
        this.addTransaction(transaction);

        return true; // Возвращаем true в случае успешного списания средств
    }
}

