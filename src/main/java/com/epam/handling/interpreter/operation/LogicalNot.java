package com.epam.handling.interpreter.operation;

import com.epam.handling.interpreter.Expression;

public class LogicalNot extends com.epam.handling.interpreter.operation.UnaryOperation {

    public LogicalNot(Expression right) {
        this.right = right;
        this.func = x -> ~x;
    }

    @Override
    public int interpret() {
        return func.apply(right.interpret());
    }
}
