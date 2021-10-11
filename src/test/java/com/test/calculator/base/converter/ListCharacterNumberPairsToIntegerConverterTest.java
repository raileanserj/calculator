package com.test.calculator.base.converter;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ListCharacterNumberPairsToIntegerConverterTest {

    private final Converter<List<Tuple2<Character, Long>>, Integer> stringListConverter = new ListCharacterNumberPairsToIntegerConverter();

    @Test
    @DisplayName("Should successfully convert")
    void shouldSuccessfullyConvert() { //50+80/(88-10)*2
        final List<Tuple2<Character, Long>> input = List.of(Tuple.of('0', 50L), Tuple.of('+', 0L), Tuple.of('0', 80L), Tuple.of('/', 0L),
            Tuple.of('(', 0L), Tuple.of('0', 88L), Tuple.of('-', 0L), Tuple.of('0', 10L), Tuple.of(')', 0L), Tuple.of('*', 0L), Tuple.of('0', 2L));

        assertThat(stringListConverter.convert(input)).isEqualTo(52);
    }

    @Test
    @DisplayName("Should truncate to int max when result is greater then int max")
    void shouldTruncateToIntMax() {
        final List<Tuple2<Character, Long>> input = List.of(Tuple.of('0', 555555555555550L), Tuple.of('+', 0L), Tuple.of('0', 555555555555550L));
        assertThat(stringListConverter.convert(input)).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("Should truncate to int min when result is below int min")
    void shouldTruncateToIntMin() {
        final List<Tuple2<Character, Long>> input = List.of(Tuple.of('0', 10L), Tuple.of('-', 0L), Tuple.of('0', 555555555555550L));
        assertThat(stringListConverter.convert(input)).isEqualTo(Integer.MIN_VALUE);
    }

    @Test
    @DisplayName("Should throw exception on division by zero")
    void shouldThrowExceptionOnDivisionByZero() {
        assertThatThrownBy(() -> stringListConverter.convert(List.of(Tuple.of('0', 10L), Tuple.of('/', 0L), Tuple.of('0', 0L))))
            .isInstanceOf(ArithmeticException.class)
            .hasMessage("Cannot divide by zero");
    }
}