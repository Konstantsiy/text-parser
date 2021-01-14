package com.epam.handling.interpreter.operation;

import com.epam.handling.interpreter.Expression;

public class Pow extends com.epam.handling.interpreter.operation.BinaryOperation {

    public Pow(Expression leftOperand, Expression rightOperand) {
        this.left = leftOperand;
        this.right = rightOperand;
        this.func = (x, y) -> (x ^ y);
    }

    @Override
    public int interpret() {
        return func.apply(left.interpret(), right.interpret());
    }
}
