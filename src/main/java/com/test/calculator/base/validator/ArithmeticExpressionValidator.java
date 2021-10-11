package com.test.calculator.base.validator;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.test.calculator.base.model.Operator.ADD;
import static com.test.calculator.base.model.Operator.isOperator;
import static com.test.calculator.base.model.Parenthesis.LEFT;
import static com.test.calculator.base.model.Parenthesis.RIGHT;
import static java.lang.Character.isDigit;

@NoArgsConstructor
public class ArithmeticExpressionValidator implements Validator {

    @Override
    public void validate(final String input) {
        final Map<Integer, String> incorrectCharSequencePositionsMap = new HashMap<>();
        incorrectCharSequencePositionsMap.putAll(validateOperatorsPositionAndReturnMapWithOperatorAndIndexMisusageIfAny(input));
        incorrectCharSequencePositionsMap.putAll(validateNumbersPositionAndReturnMapWithNumberAndIndexMisusageIfAny(input));
        incorrectCharSequencePositionsMap.putAll(validateParenthesesPositionAndReturnMapWithParenthesesAndIndexMisusageIfAny(input));
        if (incorrectCharSequencePositionsMap.size() > 0) {
            formatErrorMessageAndThrowException(incorrectCharSequencePositionsMap);
        }
    }

    private Map<Integer, String> validateOperatorsPositionAndReturnMapWithOperatorAndIndexMisusageIfAny(final String input) {
        final char[] inputChars = input.toCharArray();
        final int length = inputChars.length;
        final Map<Integer, String> incorrectOperatorPositions = new HashMap<>();
        char prevChar = ADD.getSign();

        for (int i = 0; i < length; i++) {
            char currChar = inputChars[i];
            if (isOperator(currChar)) {
                if (isOperator(prevChar) || LEFT.getSign() == currChar || i == (length - 1)) {
                    incorrectOperatorPositions.put(i, String.valueOf(currChar));
                }
            }
            prevChar = currChar;
        }
        return incorrectOperatorPositions;
    }

    private Map<Integer, String> validateNumbersPositionAndReturnMapWithNumberAndIndexMisusageIfAny(final String input) {
        final char[] inputChars = input.toCharArray();
        final int length = inputChars.length;
        final Map<Integer, String> incorrectNumberPositions = new HashMap<>();
        char prevChar = ADD.getSign();

        for (int i = 0; i < length; i++) {
            char currChar = inputChars[i];
            if (isDigit(currChar)) {
                final StringBuilder num = new StringBuilder();
                num.append(currChar);
                while (i + 1 < length && isDigit(inputChars[i + 1])) {
                    num.append(inputChars[++i]);
                }
                if (RIGHT.getSign() == prevChar) {
                    incorrectNumberPositions.put(i, num.toString());
                }
            }
            prevChar = currChar;
        }
        return incorrectNumberPositions;
    }

    private Map<Integer, String> validateParenthesesPositionAndReturnMapWithParenthesesAndIndexMisusageIfAny(final String input) {
        final char[] inputChars = input.toCharArray();
        final int length = inputChars.length;
        final Map<Integer, String> incorrectParenthesesPosition = new HashMap<>();
        char prevChar = ADD.getSign();

        for (int i = 0; i < length; i++) {
            char currChar = inputChars[i];
            if (LEFT.getSign() == currChar) {
                if (isDigit(prevChar) || RIGHT.getSign() == prevChar) {
                    incorrectParenthesesPosition.put(i, String.valueOf(currChar));
                }
            } else if (RIGHT.getSign() == currChar) {
                if (LEFT.getSign() == prevChar || isOperator(prevChar)) {
                    incorrectParenthesesPosition.put(i, String.valueOf(currChar));
                }
            }
            prevChar = currChar;
        }
        return incorrectParenthesesPosition;
    }

    private void formatErrorMessageAndThrowException(final Map<Integer, String> incorrectCharSequencePositionsMap) {
        final String errorMessage = incorrectCharSequencePositionsMap.entrySet()
            .stream()
            .map(entry -> String.format("the character sequence %s is not allowed at position %d", entry.getValue(), entry.getKey()))
            .collect(Collectors.joining(" | "));
        throw new IllegalStateException(errorMessage);
    }
}