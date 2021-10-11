package com.test.calculator.base.model;

import java.util.Arrays;

public enum Operator {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('*'),
    DIVIDE('/');
    final char sign;

    Operator(char sign) {
        this.sign = sign;
    }

    public char getSign() {
        return sign;
    }

    public static Operator getOperator(final char sign) {
        return Arrays.stream(values())
            .filter(op -> op.sign == sign)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No such operator exists: " + sign));
    }

    public static boolean isOperator(final char c) {
        return Arrays.stream(values()).anyMatch(op -> op.sign == c);
    }
}
