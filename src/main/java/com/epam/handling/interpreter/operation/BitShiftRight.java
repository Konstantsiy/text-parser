package com.epam.handling.interpreter.operation;

import com.epam.handling.interpreter.Expression;

public class BitShiftRight extends com.epam.handling.interpreter.operation.BinaryOperation {

    public BitShiftRight(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.func = (x, y) -> (x >> y);
    }

    @Override
    public int interpret() {
        return func.apply(left.interpret(), right.interpret());
    }
}
