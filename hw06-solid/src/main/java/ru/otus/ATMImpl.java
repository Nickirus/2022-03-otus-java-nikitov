package ru.otus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.otus.BanknoteDenomination.values;

public class ATMImpl implements ATM {

    private final List<BanknoteDenomination> bills = new ArrayList<>();

    @Override
    public void putMoney(BanknoteDenomination... newBills) {
        bills.addAll(Arrays.asList(newBills));
    }

    @Override
    public BanknoteDenomination[] getMoney(long sum) throws NotEnoughMoneyException, NotPossibleToWithdrawTheSpecifiedAmountException {
        if (getBalance() >= sum) {
            return giveAwayMoney(sum);
        } else {
            throw new NotEnoughMoneyException("Запрошена сумма - " + sum);
        }
    }

    @Override
    public long getBalance() {
        return bills.stream().mapToLong(BanknoteDenomination::getDenomination).sum();
    }

    private BanknoteDenomination[] giveAwayMoney(long sum) {
        long[] countsOfDenomination = new long[values().length];
        for (int i = 0; i < values().length; i++) {
            countsOfDenomination[i] = getCountByDenomination(BanknoteDenomination.values()[i]);
        }
        List<BanknoteDenomination> result = giveAwayMoneyByCountsOfBanknote(sum, new ArrayList<>(), new ArrayList<>(bills),
                countsOfDenomination);
        removeBills(result);
        return result.toArray(new BanknoteDenomination[0]);
    }

    private long getCountByDenomination(BanknoteDenomination banknoteDenomination) {
        return bills.stream().filter(banknote -> banknote.equals(banknoteDenomination)).count();
    }

    private void removeBills(List<BanknoteDenomination> billsInOrderToGive) {
        for (BanknoteDenomination banknote : billsInOrderToGive) {
            bills.remove(banknote);
        }
    }

    private List<BanknoteDenomination> giveAwayMoneyByCountsOfBanknote(long remainingSum, List<BanknoteDenomination> billsInOrderToGive,
                                                                       List<BanknoteDenomination> remainingBills,
                                                                       long... banknoteCount) {
        for (int i = 0; i < values().length; i++) {

            for (; isIterationNeeded(remainingSum, banknoteCount[i], values()[i]); banknoteCount[i] = banknoteCount[i]--) {
                remainingBills.remove(remainingBills.indexOf(values()[i]));
                billsInOrderToGive.add(values()[i]);
                remainingSum = remainingSum - values()[i].getDenomination();
                if (remainingSum == 0) return billsInOrderToGive;
            }
        }
        throw new NotPossibleToWithdrawTheSpecifiedAmountException("Нет нужного количества банкнот");
    }

    private boolean isIterationNeeded(long remainingSum, long countThisBanknote,
                                      BanknoteDenomination banknoteDenomination) {
        return countThisBanknote > 0
                && remainingSum / banknoteDenomination.getDenomination() * countThisBanknote > 0
                && remainingSum - banknoteDenomination.getDenomination() >= 0;
    }
}
