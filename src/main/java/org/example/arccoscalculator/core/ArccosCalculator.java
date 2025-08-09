package org.example.arccoscalculator.core;

public class ArccosCalculator {

  private static final double PI = 3.1415926535;
  private static final int TERMS = 25; // Number of terms in the Taylor series for arcsin

  public static boolean isValidInput(double x) {
    return x >= -1.0 && x <= 1.0;
  }

  /**
   * Compute arccos(x) using the identity: arccos(x) = π/2 - arcsin(x)
   *
   * @param x value between -1 and 1
   * @return arccos(x) in radians
   */
  public static double compute(double x) {
    return PI / 2 - arcsin(x);
  }

  /**
   * Compute arcsin(x) using Taylor series expansion: arcsin(x) = x + (1/2)*(x^3)/3 +
   * (1*3)/(2*4)*(x^5)/5 + ...
   *
   * @param x value between -1 and 1
   * @return arcsin(x) in radians
   */
  private static double arcsin(double x) {
    double result = 0.0;
    double term = x;
    double numeratorFactor = 1.0;
    double denominatorFactor = 1.0;

    for (int n = 0; n < TERMS; n++) {
      if (n > 0) {
        numeratorFactor *= (2.0 * n - 1);
        denominatorFactor *= (2.0 * n);
        term *= x * x;
      }

      double coefficient = (numeratorFactor) / (denominatorFactor * (2.0 * n + 1));
      result += coefficient * term;
    }

    return result;
  }
}
