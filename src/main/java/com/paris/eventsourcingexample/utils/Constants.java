package com.paris.eventsourcingexample.utils;

import java.math.MathContext;
import java.math.RoundingMode;

public class Constants {

    private Constants() {
    }

    public static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(0, RoundingMode.HALF_EVEN);

    public static final String NOT_FOUND = " not found";

}
