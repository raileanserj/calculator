package com.test.calculator.base.converter;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.test.calculator.base.model.Operator.ADD;
import static com.test.calculator.base.model.Operator.getOperator;
import static com.test.calculator.base.model.Operator.isOperator;
import static com.test.calculator.base.model.Parenthesis.LEFT;
import static com.test.calculator.base.model.Parenthesis.RIGHT;
import static java.lang.Character.isDigit;

@NoArgsConstructor
public class ListCharacterNumberPairsToIntegerConverter implements Converter<List<Tuple2<Character, Long>>, Integer> {

    private static final char DUMMY_CHARACTER_DIGIT_IDENTIFIER = '0';

    @Override
    public Integer convert(final List<Tuple2<Character, Long>> inputList) {
        List<Tuple2<Character, Long>> modifiableList = new ArrayList<>(inputList);

        while (hasParentheses(modifiableList)) {
            final Tuple2<Integer, Integer> leftAndRightIndex = findLeftAndRightIndexOfInnerMostParentheses(modifiableList);
            final int leftIndex = leftAndRightIndex._1();
            int rightIndex = leftAndRightIndex._2();
            final long inBetweenParenthesesExpressionResult = calculateExpression(modifiableList.subList(leftIndex + 1, rightIndex));
            modifiableList = removeExpressionFromList(modifiableList, leftIndex, rightIndex);
            modifiableList.add(leftIndex, Tuple.of(DUMMY_CHARACTER_DIGIT_IDENTIFIER, inBetweenParenthesesExpressionResult));
        }
        return resolveResult(calculateExpression(modifiableList));
    }

    private List<Tuple2<Character, Long>> removeExpressionFromList(final List<Tuple2<Character, Long>> list, final int from, final int to) {
        final List<Tuple2<Character, Long>> result = new ArrayList<>();
        result.addAll(list.subList(0, from));
        result.addAll(list.subList(to + 1, list.size()));
        return result;
    }

    private Tuple2<Integer, Integer> findLeftAndRightIndexOfInnerMostParentheses(final List<Tuple2<Character, Long>> list) {
        int leftIndex = 0;
        int rightIndex = 0;
        int currentIndex = 0;
        for (final Tuple2<Character, Long> entry : list) {
            if (LEFT.getSign() == entry._1()) {
                leftIndex = currentIndex;
            }
            if (RIGHT.getSign() == entry._1()) {
                rightIndex = currentIndex;
                break;
            }
            currentIndex++;
        }
        return Tuple.of(leftIndex, rightIndex);
    }

    private long calculateExpression(final List<Tuple2<Character, Long>> list) {
        Deque<Long> stack = new ArrayDeque<>();
        long currentNum = 0;
        char operator = ADD.getSign();
        int index = 0;
        for (final Tuple2<Character, Long> entry : list) {
            if (isDigit(entry._1())) {
                currentNum = entry._2();
            }
            if (isOperator(entry._1()) || index == list.size() - 1) {
                stack = performOperationAddToStackAndReturnNewStack(stack, currentNum, operator);
                operator = entry._1();
                currentNum = 0;
            }
            index++;
        }
        while (!stack.isEmpty()) {
            currentNum += stack.pop();
        }
        return currentNum;
    }

    private Deque<Long> performOperationAddToStackAndReturnNewStack(final Deque<Long> stack, final long num, char operator) {
        final Deque<Long> modifiableStack = new ArrayDeque<>(stack);
        switch (getOperator(operator)) {
            case ADD:
                modifiableStack.push(num);
                break;
            case SUBTRACT:
                modifiableStack.push(-num);
                break;
            case DIVIDE: {
                checkForDivisionByZero(num);
                modifiableStack.push(modifiableStack.pop() / num);
            }
            break;
            case MULTIPLY:
                modifiableStack.push(modifiableStack.pop() * num);
                break;
        }
        return modifiableStack;
    }

    private void checkForDivisionByZero(final long num) {
        if (num == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
    }

    private boolean hasParentheses(final List<Tuple2<Character, Long>> list) {
        return list.stream().anyMatch(entry -> LEFT.getSign() == entry._1());
    }

    private int resolveResult(final long rs) {
        if (rs > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (rs < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) rs;
    }
}
