package com.epam.handling.interpreter.operation;

import com.epam.handling.interpreter.Expression;

import java.util.function.Function;

public abstract class UnaryOperation implements Expression {
    protected Expression right;
    protected Function<Integer, Integer> func;
}
