package org.example.arccoscalculator.cli;

import java.util.Scanner;
import java.util.concurrent.Callable;
import org.example.arccoscalculator.core.ArccosCalculator;
import picocli.CommandLine;

@CommandLine.Command(
    name = "[arccos target]",
    mixinStandardHelpOptions = true,
    version = "Arccos Calculator 1.0",
    description = "Computes arccos(x) in radians for x ∈ [-1, 1]",
    header = "🧮 Arccos CLI Calculator",
    footer = "\nExample: java -jar arccos-calculator.jar -x 0.5")
public class CliApp implements Callable<Integer> {

  @CommandLine.Option(
      names = {"-x"},
      description = "Input value between -1 and 1")
  Double x;

  @Override
  public Integer call() {
    if (x != null) {
      processInput(x);
    } else {
      interactiveMode();
    }
    return 0;
  }

  private void interactiveMode() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\n🧮 Welcome to the Arccos Calculator CLI!");
    System.out.println("Enter a value between -1 and 1 (type 'exit' to quit):\n");

    while (true) {
      System.out.print("Please enter the value of x.\nx = ");
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("exit")) {
        System.out.println("👋 Goodbye!");
        break;
      }

      try {
        double value = Double.parseDouble(input);
        processInput(value);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.\n");
      }
    }
  }

  private void processInput(double value) {
    if (!ArccosCalculator.isValidInput(value)) {
      System.out.println("Value must be between -1 and 1.\n");
      return;
    }

    double result = ArccosCalculator.compute(value);
    System.out.printf("Arccos(%.4f) = %.4f radians\n\n", value, result);
  }

  public static void main(String[] args) {
    int exitCode = new CommandLine(new CliApp()).execute(args);
    System.exit(exitCode);
  }
}
