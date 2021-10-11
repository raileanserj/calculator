package com.test.calculator.base.sanitiser;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Sanitiser {

    public String sanitise(final String input) {
        return input.replaceAll(" ", "");
    }
}