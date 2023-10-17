package org.example.source.models;

public class Player {
    private final String playerId;
    private final String username;
    private final String password;
    private final Account account;

    public Player(String playerId, String username, String password, Account account) {
        this.playerId = playerId;
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        account = null;
        playerId = null;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Account getAccount() {
        return account;
    }
}

