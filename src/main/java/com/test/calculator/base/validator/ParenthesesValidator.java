package com.test.calculator.base.validator;

import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.test.calculator.base.model.Parenthesis.LEFT;
import static com.test.calculator.base.model.Parenthesis.RIGHT;

@NoArgsConstructor
public class ParenthesesValidator implements Validator {

    @Override
    public void validate(final String input) {
        final Deque<Integer> stack = new ArrayDeque<>();
        final char[] inputChars = input.toCharArray();
        final StringBuilder illegalClosingParenthesesIndexes = new StringBuilder();

        for (int i = 0; i < inputChars.length; i++) {
            final char current = inputChars[i];
            if (LEFT.getSign() == current) {
                stack.push(i);
            }
            if (RIGHT.getSign() == current) {
                if (stack.isEmpty()) {
                    illegalClosingParenthesesIndexes.append(" ").append(i);
                    continue;
                }
                stack.pop();
            }
        }
        checkForInvalidDataAndThrowExceptionIfExists(stack, illegalClosingParenthesesIndexes.toString());
    }

    private void checkForInvalidDataAndThrowExceptionIfExists(final Deque<Integer> stack,
                                                              final String illegalClosingParenthesesIndexes) {
        final StringBuilder errorMessageBuilder = new StringBuilder();
        if (illegalClosingParenthesesIndexes.length() > 0) {
            errorMessageBuilder.append(addIllegalClosingParenthesesToErrorMessage(illegalClosingParenthesesIndexes));
        }
        if (!stack.isEmpty()) {
            if (errorMessageBuilder.length() > 0) {
                errorMessageBuilder.append(getErrorMessageSeparator());
            }
            errorMessageBuilder.append(addIllegalOpenParenthesesToErrorMessage(stack));
        }
        if (errorMessageBuilder.length() > 0) {
            throw new IllegalStateException(errorMessageBuilder.toString());
        }
    }

    private String getErrorMessageSeparator() {
        return " | ";
    }

    private StringBuilder addIllegalClosingParenthesesToErrorMessage(final String illegalClosingParenthesesIndexes) {
        final StringBuilder errorMessageBuilder = new StringBuilder();
        errorMessageBuilder.append("Found closing parentheses without being opened, at index");
        errorMessageBuilder.append(illegalClosingParenthesesIndexes);
        return errorMessageBuilder;
    }

    private StringBuilder addIllegalOpenParenthesesToErrorMessage(final Deque<Integer> stack) {
        final StringBuilder illegalOenParentheses = new StringBuilder();
        while (!stack.isEmpty()) {
            illegalOenParentheses.insert(0, stack.pop()).insert(0, " ");
        }
        illegalOenParentheses.insert(0, "Found open parentheses without bing closed, at index");
        return illegalOenParentheses;
    }
}