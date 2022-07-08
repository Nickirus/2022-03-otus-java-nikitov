package ru.otus;

import java.util.Arrays;
import java.util.Optional;

public enum BanknoteDenomination {
    _5000(5000),
    _2000(2000),
    _1000(1000),
    _500(500),
    _200(200),
    _100(100),
    _50(50),
    _10(10);

    private final int denomination;

    BanknoteDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    static BanknoteDenomination getBanknoteByDenomination(long denomination) {
        Optional<BanknoteDenomination> resultBanknote = Arrays.stream(BanknoteDenomination.values())
                .filter(banknote -> banknote.getDenomination() == denomination)
                .findFirst();
        if (resultBanknote.isPresent()) {
            return resultBanknote.get();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
