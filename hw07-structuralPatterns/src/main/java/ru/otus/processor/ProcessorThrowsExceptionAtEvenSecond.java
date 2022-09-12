package ru.otus.processor;

import ru.otus.listener.homework.DateTimeProvider;
import ru.otus.model.Message;

public class ProcessorThrowsExceptionAtEvenSecond implements Processor {
    private final DateTimeProvider dateTimeProvider;
    public ProcessorThrowsExceptionAtEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        var second = dateTimeProvider.getDate().getSecond();
        if (second % 2 == 0) {
            throw new EvenSecondException(second);
        }

        return message;
    }
}
