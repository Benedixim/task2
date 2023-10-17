package org.example.source.services;


import org.example.source.database.DatabaseHandler;
import org.example.source.models.Player;
import org.example.source.models.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    private final AuthenticationService authenticationService;
    private final AuditService auditService;
    private Player currentPlayer;


    public MenuService() {
        this.authenticationService = new AuthenticationService();
        this.auditService = new AuditService();
        this.currentPlayer = null;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;


        while (!exit) {
            System.out.println("1. Регистрация");
            System.out.println("2. Авторизация");
            System.out.println("3. Текущий баланс");
            System.out.println("4. Дебет/снятие средств");
            System.out.println("5. Кредит/пополнение средств");
            System.out.println("6. Просмотр истории пополнения/снятия");
            System.out.println("7. Выход из аккаунта");
            System.out.println("8. Выход из программы");

            System.out.print("Выберите опцию: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> registerPlayer(scanner);
                case 2 -> login(scanner);
                case 3 -> checkBalance();
                case 4 -> debitFunds(scanner);
                case 5 -> creditFunds(scanner);
                case 6 -> viewTransactionHistory();
                case 7 -> logout();
                case 8 -> exit = true;
                default -> System.out.println("Неверная опция. Попробуйте еще раз.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private void logout() {
        if (currentPlayer == null) {
            System.out.println("Вы не авторизованы.");
            return;
        }

        currentPlayer = null;
        System.out.println("Выход выполнен успешно.");
    }

    private void registerPlayer(Scanner scanner) {

        System.out.print("Введите имя пользователя: ");
        String username = scanner.next();
        System.out.print("Введите пароль: ");
        String password = scanner.next();

        authenticationService.registerPlayer(username, password);

        System.out.println("Игрок успешно зарегистрирован.");
    }

    private void login(Scanner scanner) {
        if (currentPlayer != null) {
            System.out.println("Вы уже авторизованы.");
            return;
        }


        System.out.print("Введите имя пользователя: ");
        String username = scanner.next();
        System.out.print("Введите пароль: ");
        String password = scanner.next();

        Player player = authenticationService.login(username, password);
        if (player != null) {
            System.out.println("Авторизация успешна.");
            currentPlayer = player;
            logAction("Успешная авторизация");

        } else {
            System.out.println("Неверное имя пользователя или пароль.");
        }
    }

    private void checkBalance() {
        if (currentPlayer == null) {
            System.out.println("Авторизуйтесь в системе.");
            return;
        }


        System.out.println("Текущий баланс: " + currentPlayer.getAccount().getBalance());
        logAction("Вывод текущего баланса");
    }

    private void debitFunds(Scanner scanner) {
        if (currentPlayer == null) {
            System.out.println("Авторизуйтесь в системе.");
            return;
        }


        System.out.print("Введите сумму для дебета: ");
        double amount = scanner.nextDouble();

        if (currentPlayer.getAccount().getBalance() - amount >= 0) {

            Transaction transaction = new Transaction(currentPlayer.getPlayerId(), "Дебет", amount, new Date());
            boolean success = currentPlayer.getAccount().debitFunds(transaction);

            newTransaction(transaction);


            if (success) {
                System.out.println("Дебет успешно выполнен.");
                logAction("Успешный дебет");
            } else {
                System.out.println("Ошибка выполнения дебета.");
                logAction("Ошибка операции при выполнении дебета");
            }
        } else {
            System.out.println("Недостаточно средств на счете.");
            logAction("Недостаточно средств на счете");

        }
    }

    private void creditFunds(Scanner scanner) {
        if (currentPlayer == null) {
            System.out.println("Авторизуйтесь в системе.");
            return;
        }


        System.out.print("Введите сумму для кредита: ");
        double amount = scanner.nextDouble();

        Transaction transaction = new Transaction(currentPlayer.getPlayerId(), "Кредит", amount, new Date());
        boolean success = currentPlayer.getAccount().creditFunds(transaction);

        newTransaction(transaction);


        if (success) {
            System.out.println("Кредит успешно выполнен.");
            logAction("Успешный кредит");

        } else {
            System.out.println("Ошибка выполнения кредита.");
            logAction("Ошибка операции при выполнении кредита");
        }
    }

    private void newTransaction(Transaction transaction) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.saveTransaction(transaction);
        databaseHandler.updateBalance(currentPlayer.getAccount());
    }

    private void viewTransactionHistory() {
        if (currentPlayer == null) {
            System.out.println("Авторизуйтесь в системе.");
            return;
        }

        DatabaseHandler databaseHandler = new DatabaseHandler();
        List<Transaction> transactions = databaseHandler.getTransactions(currentPlayer.getPlayerId());

        //List<Transaction> transactions = currentPlayer.getAccount().getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("История транзакций пуста.");
        } else {
            System.out.println("История транзакций:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
        logAction("Запрос истории транзакций");

    }

    private void logAction(String action) {
        if (currentPlayer == null) {
            System.out.println("Авторизуйтесь в системе.");
            return;
        }
        auditService.logAction(currentPlayer.getPlayerId(), action);
        System.out.println("Действие успешно сохранено в журнале аудита.");
    }
}