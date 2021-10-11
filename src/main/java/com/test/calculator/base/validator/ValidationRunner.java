package com.test.calculator.base.validator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationRunner {

    private final Validator allowedCharSetValidator;

    private final Validator parenthesesValidator;

    private final Validator arithmeticExpressionValidator;

    public void runValidation(final String input) {
        allowedCharSetValidator.validate(input);
        parenthesesValidator.validate(input);
        arithmeticExpressionValidator.validate(input);
    }
}