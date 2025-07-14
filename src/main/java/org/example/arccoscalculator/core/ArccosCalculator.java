package org.example.arccoscalculator.core;

public class ArccosCalculator {

    public static boolean isValidInput(double x) {
        return x >= -1.0 && x <= 1.0;
    }

    public static double compute(double x) {
        return Math.acos(x); // returns result in radians
    }
}
