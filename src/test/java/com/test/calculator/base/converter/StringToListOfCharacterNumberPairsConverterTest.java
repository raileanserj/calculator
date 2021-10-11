package com.test.calculator.base.converter;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StringToListOfCharacterNumberPairsConverterTest {

    private final Converter<String, List<Tuple2<Character, Long>>> stringListConverter = new StringToListOfCharacterNumberPairsConverter();

    @Test
    @DisplayName("Should successfully convert")
    void shouldSuccessfullyConvert() {
        final List<Tuple2<Character, Long>> expected = List.of(Tuple.of('0', 50L), Tuple.of('+', 0L), Tuple.of('0', 80L), Tuple.of('/', 0L),
            Tuple.of('(', 0L), Tuple.of('0', 88L), Tuple.of('*', 0L), Tuple.of('0', 5L), Tuple.of(')', 0L));

        assertThat(stringListConverter.convert("50+80/(88*5)")).isEqualTo(expected);
    }
}