package com.test.calculator.base;

import com.test.calculator.base.converter.Converter;
import com.test.calculator.base.converter.ListCharacterNumberPairsToIntegerConverter;
import com.test.calculator.base.converter.StringToListOfCharacterNumberPairsConverter;
import com.test.calculator.base.sanitiser.Sanitiser;
import com.test.calculator.base.validator.AllowedCharSetValidator;
import com.test.calculator.base.validator.ArithmeticExpressionValidator;
import com.test.calculator.base.validator.ParenthesesValidator;
import com.test.calculator.base.validator.ValidationRunner;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
public class Calculator {

    public static void run() {
        final Sanitiser sanitiser = new Sanitiser();
        final ValidationRunner
            validationRunner = new ValidationRunner(new AllowedCharSetValidator(), new ParenthesesValidator(), new ArithmeticExpressionValidator());
        final Converter<String, List<Tuple2<Character, Long>>>
            stringToListOfCharacterNumberPairsConverter = new StringToListOfCharacterNumberPairsConverter();
        final Converter<List<Tuple2<Character, Long>>, Integer>
            listCharacterNumberPairsToIntegerConverter = new ListCharacterNumberPairsToIntegerConverter();
        final Scanner sc = new Scanner(System.in);
        final String exitWord = "exit";

        System.out.println("Welcome to Basic Calculator App");

        for (; ; ) {
            System.out.println("To perform an operation, type in the math expression and press enter");
            System.out.println("To exit, type: \"" + exitWord + "\" and press enter");
            final String expressionToCalculate = sc.nextLine();

            checkExit(expressionToCalculate, exitWord);
            Try.of(() -> sanitiser.sanitise(expressionToCalculate))
                .andThen(validationRunner::runValidation)
                .map(stringToListOfCharacterNumberPairsConverter::convert)
                .map(listCharacterNumberPairsToIntegerConverter::convert)
                .onSuccess(System.out::println)
                .onFailure(e -> System.out.println("Error during processing, error message: " + e.getMessage()));
        }
    }

    private static void checkExit(final String input, final String exitWord) {
        if (exitWord.equalsIgnoreCase(input)) {
            System.out.println("Terminating upon callers request");
            System.exit(0);
        }
    }
}
