package ru.otus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.otus.BanknoteDenomination._5000;
import static ru.otus.BanknoteDenomination.getBanknoteByDenomination;

public class ATMImpl implements ATM {

    private final List<BanknoteDenomination> bills = new ArrayList<>();

    @Override
    public void putMoney(BanknoteDenomination... newBills) {
        bills.addAll(Arrays.asList(newBills));
//        bills.sort((o1, o2) -> o1.getDenomination() > o2.getDenomination());
    }

    @Override
    public BanknoteDenomination[] getMoney(long sum) throws NotEnoughMoneyException, NotPossibleToWithdrawTheSpecifiedAmountException {
        if (getBalance() >= sum) {
            checkThePossibilityOfIssuingMoney(sum);
            return giveAwayMoney(sum);
        } else {
            throw new NotEnoughMoneyException();
        }
    }

    @Override
    public long getBalance() {
        return bills.stream().mapToLong(BanknoteDenomination::getDenomination).sum();
    }

    // TODO: 07.07.2022 не реализовано
    private BanknoteDenomination[] giveAwayMoney(long sum) {
//        return new BanknoteDenomination[]{_5000};


        List<BanknoteDenomination> result = new ArrayList<>();
        long[] denomination = bills.stream().mapToLong(BanknoteDenomination::getDenomination).toArray();
        int i = 0, j, k = denomination.length;
        while (k != 0 && denomination[--k] > sum)
            ;
        j = k;
        long tempSum, count;
        do {
            if ((tempSum = sum % denomination[j]) >= denomination[0] || tempSum == 0) {
                count = sum / denomination[j];
                sum = tempSum;
            } else {
                count = sum / denomination[j] - 1;
                sum = tempSum + denomination[j];
            }
            System.out.println("Denomination: " + denomination[j] + "; count: " + count);
            for (int l = 0; l < count; l++) {
                result.add(getBanknoteByDenomination(denomination[j]));
            }
            while (j != 0 && denomination[--j] > sum)
                ;
        } while (i < k && sum > 0);
        return result.toArray(new BanknoteDenomination[0]);
    }

    // TODO: 07.07.2022 Должно быть хитрее
    private void checkThePossibilityOfIssuingMoney(long sum) {
//        if (!(getBalance() % sum == 0)) {
//            throw new NotPossibleToWithdrawTheSpecifiedAmountException();
//        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATMImpl atm = (ATMImpl) o;
        return Objects.equals(bills, atm.bills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bills);
    }
}
