package ru.otus;

import java.util.Arrays;

import static ru.otus.BanknoteDenomination.*;

public class App {
    public static void main(String[] args) {
        ATM atm = new ATMImpl();
        System.out.println("Первоначальный баланс: " + atm.getBalance());

        atm.putMoney(_5000, _5000, _2000, _50, _10);
        System.out.println("\nБаланс после пополнения (купюрами 5000, 5000, 2000, 50, 10): " + atm.getBalance());

        System.out.println("\nПолучили: " + Arrays.toString(atm.getMoney(7050)));
        System.out.println("Баланс после выдачи 7050 руб.: " + atm.getBalance());

        System.out.println("\nПолучили: " + Arrays.toString(atm.getMoney(10)));
        System.out.println("Баланс после выдачи 10 руб.: " + atm.getBalance());

        System.out.println("\nПопытка получить 12060 руб.: ");
        try {
            System.out.println(Arrays.toString(atm.getMoney(12060)));
        } catch (NotEnoughMoneyException e) {
            System.out.println("Исключение с сообщением: " + e.getMessage());
        }

        System.out.println("\nПопытка получить 3000 руб.: ");
        try {
            System.out.println(Arrays.toString(atm.getMoney(3000)));
        } catch (NotPossibleToWithdrawTheSpecifiedAmountException e) {
            System.out.println("Исключение с сообщением: " + e.getMessage());
        }

        System.out.println("\nПолучили: " + Arrays.toString(atm.getMoney(5000)));
        System.out.println("Баланс после выдачи 5000 руб.: " + atm.getBalance());
    }
}
