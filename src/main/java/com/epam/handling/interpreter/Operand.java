package com.epam.handling.interpreter;


public class Operand implements Expression {
    private final int number;

    public Operand(int number) {
        this.number = number;
    }

    @Override
    public int interpret() {
        return number;
    }
}
