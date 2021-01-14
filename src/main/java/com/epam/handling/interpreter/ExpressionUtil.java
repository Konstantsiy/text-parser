package com.epam.handling.interpreter;

import com.epam.handling.entity.Operation;
import com.epam.handling.interpreter.operation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class ExpressionUtil {
    private static final String OPENING_BRACKET = "(";
    private static final String CLOSING_BRACKET = ")";

    private static final String EXPRESSION_DELIMITER = " ";
    private static final String TOKEN_REGEX = " +";


    public static boolean isBinaryOperator(String operation) {
        return operation.equals(Operation.LOGICAL_AND) || operation.equals(Operation.LOGICAL_OR)
                || operation.equals(Operation.BIT_SHIFT_LEFT) || operation.equals(Operation.BIT_SHIFT_RIGHT) ||
                operation.equals(Operation.POW);
    }

    public static boolean isUnaryOperator(String operation) {
        return operation.equals(Operation.LOGICAL_NOT);
    }

    public static Optional<Expression> getOperator(String operator, Expression left, Expression right) {
        switch(operator) {
            case Operation.LOGICAL_AND: return Optional.of(new LogicalAnd(left, right));
            case Operation.LOGICAL_OR: return Optional.of(new LogicalOr(left, right));
            case Operation.BIT_SHIFT_LEFT: return Optional.of(new BitShiftLeft(left, right));
            case Operation.BIT_SHIFT_RIGHT: return Optional.of(new BitShiftRight(left, right));
            case Operation.POW: return Optional.of(new Pow(left, right));
            default: return Optional.empty();
        }
    }

    public static String buildReversePolishRecord(String expression) {
        Stack<String> stack = new Stack<>();
        List<String> resultList = new ArrayList<>();
        boolean unaryFlag = false;
        String tempForUnaryOperator = null;
        for(String c : expression.split(EXPRESSION_DELIMITER)) {
            if(isBinaryOperator(c) || c.equals(OPENING_BRACKET)) {
                stack.push(c);
            } else {
                if(isUnaryOperator(c)) {
                    tempForUnaryOperator = c;
                    unaryFlag = true;
                    continue;
                }
                if(c.equals(CLOSING_BRACKET)) {
                    while(!stack.isEmpty()) {
                        String el = stack.pop();
                        if(!el.equals(OPENING_BRACKET)) {
                            resultList.add(el);
                        }
                    }
                } else {
                    resultList.add(c);
                    if(unaryFlag) {
                        resultList.add(tempForUnaryOperator);
                        unaryFlag = false;
                    }
                }
            }
        }
        while(!stack.isEmpty()) {
            String el = stack.pop();
            if(!el.equals(OPENING_BRACKET)) {
                resultList.add(el + EXPRESSION_DELIMITER);
            }
        }
        StringBuilder result = new StringBuilder();
        for(String s : resultList) {
            result.append(s).append(EXPRESSION_DELIMITER);
        }
        return result.toString();
    }

    public static int calculateReversePolishRecord(String token) {
        Stack<Expression> stack = new Stack<>();
        String[] tokenArray = token.split(TOKEN_REGEX);
        for (String s : tokenArray) {
            if(isBinaryOperator(s)) {
                Expression right = stack.pop();
                Expression left = stack.pop();
                Expression operator = getOperator(s, left,right).get();
                int result = operator.interpret();
                stack.push(new Operand(result));
            } else if(isUnaryOperator(s)) {
                Expression right = stack.pop();
                Expression operator = new LogicalNot(right);
                int result = operator.interpret();
                stack.push(new Operand(result));
            } else {
                Expression i = new Operand(Integer.parseInt(s));
                stack.push(i);
            }
        }
        return stack.pop().interpret();
    }
}
