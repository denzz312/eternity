package org.example.arccoscalculator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.arccoscalculator.core.ArccosCalculator;

import java.text.DecimalFormat;

public class ArccosController {

    @FXML
    private TextField inputField;

    @FXML
    private Label resultLabel;

    private final DecimalFormat formatter = new DecimalFormat("#.####");

    @FXML
    protected void onCalculateClick() {
        String inputText = inputField.getText().trim();

        try {
            double x = Double.parseDouble(inputText);

            if (!ArccosCalculator.isValidInput(x)) {
                resultLabel.setText("Input must be between -1 and 1.");
                resultLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            double result = ArccosCalculator.compute(x);
            resultLabel.setText("arccos(" + x + ") = " + formatter.format(result) + " radians");
            resultLabel.setStyle("-fx-text-fill: black;");

        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input. Please enter a number.");
            resultLabel.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            resultLabel.setText("An error occurred: " + e.getMessage());
            resultLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
