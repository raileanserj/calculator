package com.test.calculator.base.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArithmeticExpressionValidatorTest {

    private final Validator parenthesesValidator = new ArithmeticExpressionValidator();

    @Test
    @DisplayName("Should successfully validate input parentheses")
    void shouldSuccessfullyValidateInput() {
        assertThatCode(() -> parenthesesValidator.validate("25+52/64+(3*(6+8))")).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "{index} should throw exception with message: {1} for input {0}")
    @MethodSource("InvalidArithmeticExpressionSource")
    @DisplayName("Should throw exception if closing parentheses without being opened encountered")
    void shouldThrowExceptionOnInvalidParentheses(final String input, final String expectedErrorMessage) {
        assertThatThrownBy(() -> parenthesesValidator.validate(input))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage(expectedErrorMessage);
    }

    private static Stream<Arguments> InvalidArithmeticExpressionSource() {
        return Stream.of(
            Arguments.of("+888+-(+", "the character sequence + is not allowed at position 0 | the character sequence - is not allowed at position 5 "
                + "| the character sequence + is not allowed at position 7"),
            Arguments.of("(88+9)899", "the character sequence 899 is not allowed at position 8"),
            Arguments.of("6(88+9)(899",
                "the character sequence ( is not allowed at position 1 | the character sequence ( is not allowed at position 7"),
            Arguments.of("(688+9+()+89+)+9",
                "the character sequence ) is not allowed at position 8 | the character sequence ) is not allowed at position 13")
        );
    }
}