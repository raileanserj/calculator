package com.test.calculator.base.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AllowedCharSetValidatorTest {

    private final Validator allowedCharSetValidator = new AllowedCharSetValidator();

    @Test
    @DisplayName("Should successfully validate input")
    void shouldSuccessfullyValidateInput() {
        assertThatCode(() -> allowedCharSetValidator.validate("0123456789+-*/()")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Should throw exception if forbidden characters are encountered")
    void shouldThrowExceptionOnInvalidCharInput() {
        assertThatThrownBy(() -> allowedCharSetValidator.validate("012invalid%$@#$/*"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("the following characters ar not allowed: i, n, v, a, l, i, d, %, $, @, #, $");
    }
}