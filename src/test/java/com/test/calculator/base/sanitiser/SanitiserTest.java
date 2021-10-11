package com.test.calculator.base.sanitiser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SanitiserTest {

    private final Sanitiser sanitiser = new Sanitiser();

    @Test
    @DisplayName("Should successfully remove whitespaces")
    void shouldSuccessfullySanitise() {
        final String expected = "0123456789-+*/()";
        final String actual = sanitiser.sanitise("0  12  34  56 7 8     9-    + * / ( )   ");
        assertThat(actual).isEqualTo(expected);
    }
}