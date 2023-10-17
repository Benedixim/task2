package org.example.source.models;

import java.util.Date;

public class Transaction {
    private final String transactionId;
    private final String playerId;
    private final String type;
    private final double amount;

    private final Date date;

    public Transaction(String transactionId, String playerId, String type, double amount, Date date) {
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(String playerId, String type, double amount, Date date) {
        this.playerId = playerId;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.transactionId = null;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
