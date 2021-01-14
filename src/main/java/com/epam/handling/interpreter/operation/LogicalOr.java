package com.epam.handling.interpreter.operation;

import com.epam.handling.interpreter.Expression;

public class LogicalOr extends com.epam.handling.interpreter.operation.BinaryOperation {

    public LogicalOr(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.func = (x, y) -> (x | y);
    }

    @Override
    public int interpret() {
        return func.apply(left.interpret(), right.interpret());
    }
}
