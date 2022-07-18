package ru.otus;

public interface ATM {

    void putMoney(BanknoteDenomination... bills);

    BanknoteDenomination[] getMoney(long sum) throws NotEnoughMoneyException;

    long getBalance();
}
