package com.test.calculator.base.model;

import java.util.Arrays;

public enum Parenthesis {
    LEFT('('),
    RIGHT(')');

    final char sign;

    Parenthesis(final char sign) {
        this.sign = sign;
    }

    public char getSign() {
        return sign;
    }

    public static boolean isParentheses(final char c) {
        return Arrays.stream(values()).anyMatch(pr -> pr.sign == c);
    }
}
