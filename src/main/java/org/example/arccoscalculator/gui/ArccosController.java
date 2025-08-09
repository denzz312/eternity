package org.example.arccoscalculator.gui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import org.example.arccoscalculator.core.ArccosCalculator;

public class ArccosController {

  @FXML private TextField inputField;
  @FXML private Button calcBtn;
  @FXML private Label resultLabel;
  @FXML private Label messageLabel;

  private static final double MIN = -1.0;
  private static final double MAX =  1.0;

  @FXML
  public void initialize() {
    inputField.setTooltip(new Tooltip("Enter a number between -1 and 1 inclusive"));

    // Allow typing freely; show/clear message based on validity (don't block edits)
    StringConverter<Double> conv = new DoubleStringConverter();
    TextFormatter<Double> tf = new TextFormatter<>(conv, null, change -> {
      String s = change.getControlNewText().trim();

      // allow intermediate states while typing
      if (s.isEmpty() || s.equals("-") || s.equals(".") || s.equals("-.")) {
        clearStatus();
        return change;
      }

      try {
        double val = Double.parseDouble(s);
        if (val < MIN || val > MAX) {
          showError("Value out of range. Enter a number between -1 and 1.");
        } else {
          clearStatus();
        }
      } catch (NumberFormatException ex) {
        showError("Invalid number. Try examples like 0.5, -0.2, or 1.");
      }
      return change; // never block; just show status
    });
    inputField.setTextFormatter(tf);

    // Disable Calculate unless input parses AND is within [-1, 1]
    calcBtn.disableProperty().bind(
            Bindings.createBooleanBinding(() -> {
              String t = inputField.getText() == null ? "" : inputField.getText().trim();
              if (t.isEmpty() || t.equals("-") || t.equals(".") || t.equals("-.")) return true;
              try {
                double v = Double.parseDouble(t);
                return v < MIN || v > MAX;
              } catch (NumberFormatException e) {
                return true;
              }
            }, inputField.textProperty())
    );
  }

  @FXML
  protected void onCalculateClick() {
    try {
      String raw = inputField.getText().trim();
      if (raw.isEmpty()) {
        showError("Please enter a value.");
        return;
      }
      double x = Double.parseDouble(raw);
      if (!ArccosCalculator.isValidInput(x)) {
        showError("Value out of range. Enter a number between -1 and 1.");
        return;
      }
      double rad = ArccosCalculator.compute(x);
      String formatted = String.format("%.4f", rad);
      resultLabel.setText(formatted + " rad");
      showOk("Success. Result shown above.");
    } catch (NumberFormatException ex) {
      showError("Invalid number. Try examples like 0.5, -0.2, or 1.");
    } catch (Exception ex) {
      showError("Unexpected error: " + ex.getMessage());
    }
  }

  @FXML
  protected void onClear() {
    inputField.clear();
    resultLabel.setText("");
    clearStatus();
    inputField.requestFocus();
  }

  @FXML
  protected void onHelp() {
    Alert a = new Alert(Alert.AlertType.INFORMATION);
    a.setTitle("Help");
    a.setHeaderText("How to use");
    a.setContentText("Enter x in [-1, 1] and click Calculate.\n"
            + "The result is arccos(x) in radians with 4 decimal places.\n"
            + "Errors are shown below in red.");
    a.showAndWait();
  }

  private void showError(String msg) {
    messageLabel.setText(msg);
    messageLabel.getStyleClass().removeAll("ok");
    if (!messageLabel.getStyleClass().contains("error")) {
      messageLabel.getStyleClass().add("error");
    }
  }

  private void showOk(String msg) {
    messageLabel.setText(msg);
    messageLabel.getStyleClass().removeAll("error");
    if (!messageLabel.getStyleClass().contains("ok")) {
      messageLabel.getStyleClass().add("ok");
    }
  }

  private void clearStatus() {
    messageLabel.setText("");
    messageLabel.getStyleClass().removeAll("error", "ok");
  }
}
