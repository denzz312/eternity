package org.example.arccoscalculator.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.List;

class ArccosCalculatorTest {

    private static final double PI = 3.1415926535;
    private static final double EPS = 0.1;

    @Test
    @DisplayName("isValidInput accepts exactly [-1, 1]")
    void isValidInput_domain() {
        assertTrue(ArccosCalculator.isValidInput(-1.0));
        assertTrue(ArccosCalculator.isValidInput(0.0));
        assertTrue(ArccosCalculator.isValidInput(1.0));
        assertFalse(ArccosCalculator.isValidInput(-1.0001));
        assertFalse(ArccosCalculator.isValidInput(1.0001));
    }

    @ParameterizedTest(name = "arccos({0}) ≈ {1} rad")
    @CsvSource({
            "1.0, 0.0",
            "0.5, 1.0471975512",
            "0.0, 1.5707963267",
            "-0.5, 2.0943951029",
            "-1.0, 3.1415926535"
    })
    @DisplayName("Exact-angle identities at key points")
    void arccos_exact_identities(double x, double expected) {
        double actual = ArccosCalculator.compute(x);
        assertEquals(expected, actual, EPS);
    }

    @ParameterizedTest(name = "symmetry: arccos(-{0}) ≈ PI - arccos({0})")
    @CsvSource({ "0.1", "0.3", "0.5", "0.7", "0.9" })
    @DisplayName("Symmetry: arccos(-x) = PI - arccos(x)")
    void symmetry_property(double x) {
        double a = ArccosCalculator.compute(x);
        double b = ArccosCalculator.compute(-x);
        assertEquals(PI - a, b, EPS);
    }

    @Test
    @DisplayName("Monotonicity: arccos(x) is non-increasing on [-1, 1]")
    void monotonicity_nonIncreasing() {
        List<Double> xs = List.of(-1.0, -0.5, 0.0, 0.5, 0.7071067812, 0.8660254038, 1.0);
        double prev = Double.POSITIVE_INFINITY;
        for (double x : xs) {
            double y = ArccosCalculator.compute(x);
            assertTrue(y <= prev + EPS, "Not non-increasing at x=" + x);
            prev = y;
        }
    }

    @Test
    @DisplayName("Out-of-range: compute should throw")
    void compute_outOfRange_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> ArccosCalculator.compute(1.0001));
        assertThrows(IllegalArgumentException.class, () -> ArccosCalculator.compute(-1.0001));
    }
}