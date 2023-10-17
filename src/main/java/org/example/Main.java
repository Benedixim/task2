package org.example;

import org.example.source.services.MenuService;


public class Main {
    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        menuService.start();
    }
}