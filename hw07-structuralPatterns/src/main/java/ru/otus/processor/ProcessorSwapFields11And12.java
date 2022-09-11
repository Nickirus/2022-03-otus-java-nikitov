package ru.otus.processor;

import ru.otus.model.Message;

public class ProcessorSwapFields11And12 implements Processor {
    @Override
    public Message process(Message message) {
        String buffer = message.getField11();
        return message.toBuilder()
                .field11(message.getField12())
                .field12(buffer)
                .build();
    }
}
