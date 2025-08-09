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
    // Informative tooltip (visible help)
    inputField.setTooltip(new Tooltip("Enter a number between -1 and 1 inclusive"));

    calcBtn.disableProperty().bind(
            javafx.beans.binding.Bindings.createBooleanBinding(() -> {
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

    // Keyboard accelerator: Ctrl/Cmd + Enter triggers Calculate
    inputField.sceneProperty().addListener((obs, oldScene, scene) -> {
      if (scene == null) return;
      scene.getAccelerators().put(
              new javafx.scene.input.KeyCodeCombination(
                      javafx.scene.input.KeyCode.ENTER,
                      javafx.scene.input.KeyCombination.SHORTCUT_DOWN),
              this::onCalculateClick
      );
    });
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
    messageLabel.setText(msg);                    // text changes are announced
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
    messageLabel.getStyleClass().removeAll("error","ok");
  }
}
