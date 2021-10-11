package com.test.calculator.base.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParenthesesValidatorTest {

    private final Validator parenthesesValidator = new ParenthesesValidator();

    @Test
    @DisplayName("Should successfully validate input parentheses")
    void shouldSuccessfullyValidateInput() {
        assertThatCode(() -> parenthesesValidator.validate("((/)((545*/*)()8-+))")).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "{index} should throw exception with message: {1} for input {0}")
    @MethodSource("InvalidParenthesesSource")
    @DisplayName("Should throw exception if invalid parentheses usage encountered")
    void shouldThrowExceptionOnInvalidParentheses(final String input, final String expectedErrorMessage) {
        assertThatThrownBy(() -> parenthesesValidator.validate(input))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(expectedErrorMessage);
    }

    private static Stream<Arguments> InvalidParenthesesSource() {
        return Stream.of(
            Arguments.of("(000)))15789)", "Found closing parentheses without being opened, at index 5 6 12"),
            Arguments.of("(0(0(0)))157(((89", "Found open parentheses without bing closed, at index 12 13 14"),
            Arguments.of("(000)))15789)(()(",
                "Found closing parentheses without being opened, at index 5 6 12 | Found open parentheses without bing closed, at index 13 16")
        );
    }
}