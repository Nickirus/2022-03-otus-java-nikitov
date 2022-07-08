package ru.otus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.otus.BanknoteDenomination.*;

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
            throw new NotEnoughMoneyException("Недостаточно средств");
        }
    }

    @Override
    public long getBalance() {
        return bills.stream().mapToLong(BanknoteDenomination::getDenomination).sum();
    }

    private BanknoteDenomination[] giveAwayMoney(long sum) {
        long count5000 = getCountByDenomination(_5000);
        long count2000 = getCountByDenomination(_2000);
        long count1000 = getCountByDenomination(_1000);
        long count500 = getCountByDenomination(_500);
        long count200 = getCountByDenomination(_200);
        long count100 = getCountByDenomination(_100);
        long count50 = getCountByDenomination(_50);
        long count10 = getCountByDenomination(_10);

        List<BanknoteDenomination> result = giveAwayMoneyByCountsOfBanknote(new ArrayList<>(), new ArrayList<>(bills),
                sum, count5000, count2000, count1000, count500, count200, count100, count50, count10);
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

    private List<BanknoteDenomination> giveAwayMoneyByCountsOfBanknote(List<BanknoteDenomination> billsInOrderToGive,
                                                                       List<BanknoteDenomination> remainingBills,
                                                                       long remainingSum, long count5000, long count2000,
                                                                       long count1000, long count500, long count200,
                                                                       long count100, long count50, long count10) {
        for (; isIterationNeeded(remainingSum, count5000, _5000); count5000--) {
            remainingBills.remove(remainingBills.indexOf(_5000));
            billsInOrderToGive.add(_5000);
            remainingSum = remainingSum - _5000.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
        }
        for (; isIterationNeeded(remainingSum, count2000, _2000); count2000--) {
            remainingBills.remove(remainingBills.indexOf(_2000));
            billsInOrderToGive.add(_2000);
            remainingSum = remainingSum - _2000.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
        }
        for (; isIterationNeeded(remainingSum, count1000, _1000); count1000--) {
            remainingBills.remove(remainingBills.indexOf(_1000));
            billsInOrderToGive.add(_1000);
            remainingSum = remainingSum - _1000.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
        }
        for (; isIterationNeeded(remainingSum, count500, _500); count500--) {
            remainingBills.remove(remainingBills.indexOf(_500));
            billsInOrderToGive.add(_500);
            remainingSum = remainingSum - _500.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
        }
        for (; isIterationNeeded(remainingSum, count200, _200); count200--) {
            remainingBills.remove(remainingBills.indexOf(_200));
            billsInOrderToGive.add(_200);
            remainingSum = remainingSum - _200.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
        }
        for (; isIterationNeeded(remainingSum, count100, _100); count100--) {
            remainingBills.remove(remainingBills.indexOf(_100));
            billsInOrderToGive.add(_100);
            remainingSum = remainingSum - _100.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
        }
        for (; isIterationNeeded(remainingSum, count50, _50); count50--) {
            remainingBills.remove(remainingBills.indexOf(_50));
            billsInOrderToGive.add(_50);
            remainingSum = remainingSum - _50.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
        }
        for (; isIterationNeeded(remainingSum, count10, _10); count10--) {
            remainingBills.remove(remainingBills.indexOf(_10));
            billsInOrderToGive.add(_10);
            remainingSum = remainingSum - _10.getDenomination();
            if (remainingSum == 0) return billsInOrderToGive;
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
