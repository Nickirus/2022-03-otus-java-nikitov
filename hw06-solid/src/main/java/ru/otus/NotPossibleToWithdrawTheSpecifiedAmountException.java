package ru.otus;

/**
 * Исключение, которое выбрасывается, если нет достаточного количества банкнот
 * с нужным номиналом для выдачи запрошенной суммы
 */
public class NotPossibleToWithdrawTheSpecifiedAmountException extends RuntimeException {
    public NotPossibleToWithdrawTheSpecifiedAmountException() {
        this(null, null);
    }

    public NotPossibleToWithdrawTheSpecifiedAmountException(String message) {
        this(message, null);
    }

    public NotPossibleToWithdrawTheSpecifiedAmountException(Throwable cause) {
        this(cause != null ? cause.getMessage() : null, cause);
    }

    public NotPossibleToWithdrawTheSpecifiedAmountException(String message, Throwable cause) {
        super(message);
        if (cause != null) super.initCause(cause);
    }
}
