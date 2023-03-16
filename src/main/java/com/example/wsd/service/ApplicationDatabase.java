package com.example.wsd.service;

import com.example.wsd.factory.AtmFactory;

public class ApplicationDatabase {
    public static void main(String[] args) {
        AtmService service = AtmFactory.getService();

        boolean clearDatabase = true;
//        if (clearDatabase) {
//            System.err.println("Clearing Database");
//        }

//        ATM LoginCommandAlice = service.doLogin("Alice");
//        ATM LoginCommandWibowo = service.doLogin("Wibowo");
//        ATM LoginCommandFandi = service.doLogin("Fandi");

        System.out.println("Printing All Tasks");
        //service.findAll().forEach(System.out::println);
    }
}
