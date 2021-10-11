package com.test.calculator.base.validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ValidationRunnerTest {

    @Mock
    private Validator allowedCharSetValidator;

    @Mock
    private Validator parenthesesValidator;

    @Mock
    private Validator arithmeticExpressionValidator;

    private ValidationRunner validationRunner;

    @BeforeEach
    void setUp() {
        validationRunner = new ValidationRunner(allowedCharSetValidator, parenthesesValidator, arithmeticExpressionValidator);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(allowedCharSetValidator, parenthesesValidator, arithmeticExpressionValidator);
    }

    @Test
    @DisplayName("Should successfully call each instance of validator")
    void shouldSuccessfullyCallValidators() {
        final String inputToValidate = "564+15*(51)/515+";

        validationRunner.runValidation(inputToValidate);

        verify(allowedCharSetValidator).validate(inputToValidate);
        verify(parenthesesValidator).validate(inputToValidate);
        verify(arithmeticExpressionValidator).validate(inputToValidate);
    }
}