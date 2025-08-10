package org.example.arccoscalculator.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

@DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = "true") // skip on CI
@Timeout(15)
class ArccosControllerTest extends ApplicationTest {

    private ArccosController controller;
    private TextField inputField;
    private Button calcBtn;
    private Label resultLabel;
    private Label messageLabel;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arccoscalculator/gui/arccos-view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setup() {
        // Assign fields from controller (if not already done in start)
        inputField = controller.inputField;
        calcBtn = controller.calcBtn;
        resultLabel = controller.resultLabel;
        messageLabel = controller.messageLabel;
    }

    @Test
    void testValidInput_calculatesCorrectly() {
        interact(() -> inputField.setText("0.5"));
        interact(() -> controller.onCalculateClick());
        assertTrue(resultLabel.getText().startsWith("1.047"), "Result should be arccos(0.5)");
        assertTrue(messageLabel.getText().contains("Success"));
    }

    @Test
    void testInvalidInput_showsError() {
        interact(() -> inputField.setText("2"));
        interact(() -> controller.onCalculateClick());
        assertTrue(messageLabel.getText().contains("out of range"));
        assertEquals("", resultLabel.getText());
    }

    @Test
    void testNonNumberInput_showsError() {
        interact(() -> inputField.setText("abc"));
        interact(() -> controller.onCalculateClick());
        assertTrue(messageLabel.getText().contains("Invalid number"));
        assertEquals("", resultLabel.getText());
    }

    @Test
    void testEmptyInput_showsError() {
        interact(() -> inputField.setText(""));
        interact(() -> controller.onCalculateClick());
        assertTrue(messageLabel.getText().contains("Please enter a value"));
        assertEquals("", resultLabel.getText());
    }

    @Test
    void testClear_resetsFields() {
        interact(() -> {
            inputField.setText("0.5");
            resultLabel.setText("1.0472 rad");
            messageLabel.setText("Success");
            controller.onClear();
        });
        assertEquals("", inputField.getText());
        assertEquals("", resultLabel.getText());
        assertEquals("", messageLabel.getText());
    }
}