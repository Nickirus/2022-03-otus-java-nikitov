package ru.otus;

import java.util.Arrays;

import static ru.otus.BanknoteDenomination.*;

public class App {
    public static void main(String[] args) {
        ATM atm = new ATMImpl();
        System.out.println(atm.getClass());
        System.out.println(atm.getBalance());
        atm.putMoney(_5000, _2000, _50);
        System.out.println(atm.getBalance());
        System.out.println(Arrays.toString(atm.getMoney(7050)));
    }
}
