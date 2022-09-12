package ru.otus.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorThrowsExceptionAtEvenSecondTest {


    @Test
    @DisplayName("Assert does not throw exception")
    void processAtOddSecond() {
        Processor processor = new ProcessorThrowsExceptionAtEvenSecond(() ->
                LocalDateTime.of(2022, Month.SEPTEMBER, 5, 22, 50, 1)
        );
        assertDoesNotThrow(() -> processor.process(new Message.Builder(1L).field1("").build()));
    }

    @Test
    @DisplayName("Assert EvenSecondException")
    void processAtEvenSecond() {
        Processor processor = new ProcessorThrowsExceptionAtEvenSecond(() ->
                LocalDateTime.of(2022, Month.SEPTEMBER, 5, 22, 50, 2)
        );
        assertThrows(EvenSecondException.class, () -> processor.process(new Message.Builder(1L).field1("").build()));
    }
}