package ru.otus.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public final class MyMeasurement {
    private String name;
    private double value;

    public Measurement toMeasurement() {
        return new Measurement(name, value);
    }
}
