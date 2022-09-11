package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorThrowsExceptionAtEvenSecond implements Processor {
    private final LocalDateTime time;
    public ProcessorThrowsExceptionAtEvenSecond(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public Message process(Message message) {
        var second = time.getSecond();
        if (second % 2 == 0) {
            throw new EvenSecondException(second);
        }

        return message;
    }
}
