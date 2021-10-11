package com.test.calculator.base.converter;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;

@NoArgsConstructor
public class StringToListOfCharacterNumberPairsConverter implements Converter<String, List<Tuple2<Character, Long>>> {

    private static final char DUMMY_CHARACTER_DIGIT_IDENTIFIER = '0';

    private static final long DUMMY_UNUSED_NUMERICAL_VALUE = 0L;

    @Override
    public List<Tuple2<Character, Long>> convert(final String source) {
        final List<Tuple2<Character, Long>> pairs = new ArrayList<>();
        final char[] inputArray = source.toCharArray();
        final int length = inputArray.length;

        for (int i = 0; i < length; i++) {
            final char currentChar = inputArray[i];
            if (isDigit(currentChar)) {
                long currentNum = getNumericValue(currentChar);
                while ((i + 1 < length) && isDigit(inputArray[i + 1])) {
                    currentNum = currentNum * 10 + (getNumericValue(inputArray[++i]));
                }
                pairs.add(Tuple.of(DUMMY_CHARACTER_DIGIT_IDENTIFIER, currentNum));
            } else {
                pairs.add((Tuple.of(currentChar, DUMMY_UNUSED_NUMERICAL_VALUE)));
            }
        }
        return pairs;
    }
}
