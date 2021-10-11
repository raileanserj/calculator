package com.test.calculator.base.converter;

public interface Converter<S, T> {
    T convert(S source);
}