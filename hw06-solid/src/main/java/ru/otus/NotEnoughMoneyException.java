package ru.otus;

/**
 * Исключение, которое выбрасывается, если недостаточно средств в банкомате
 */
public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        this(null, null);
    }

    public NotEnoughMoneyException(String message) {
        this(message, null);
    }

    public NotEnoughMoneyException(Throwable cause) {
        this(cause != null ? cause.getMessage() : null, cause);
    }

    public NotEnoughMoneyException(String message, Throwable cause) {
        super(message);
        if (cause != null) super.initCause(cause);
    }
}
