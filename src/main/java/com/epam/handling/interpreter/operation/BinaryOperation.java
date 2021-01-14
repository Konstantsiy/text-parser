package com.epam.handling.interpreter.operation;

import com.epam.handling.interpreter.Expression;

import java.util.function.BiFunction;

public abstract class BinaryOperation implements Expression {
    protected Expression left;
    protected Expression right;
    protected BiFunction<Integer, Integer, Integer> func;
}
