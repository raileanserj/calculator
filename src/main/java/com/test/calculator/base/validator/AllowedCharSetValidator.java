package com.test.calculator.base.validator;

import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

import static com.test.calculator.base.model.Operator.isOperator;
import static com.test.calculator.base.model.Parenthesis.isParentheses;
import static java.lang.Character.isDigit;

@NoArgsConstructor
public class AllowedCharSetValidator implements Validator {

    @Override
    public void validate(final String input) {
        final String forbiddenChars = input.chars()
            .mapToObj(c -> (char) c)
            .filter(this::notIsDigitOperatorOrParenthesis)
            .map(String::valueOf)
            .collect(Collectors.joining(", "));
        if (forbiddenChars.length() > 0) {
            throw new IllegalArgumentException(String.format("the following characters ar not allowed: %s", forbiddenChars));
        }
    }

    private boolean notIsDigitOperatorOrParenthesis(final char c) {
        return !(isDigit(c) || isOperator(c) || isParentheses(c));
    }
}