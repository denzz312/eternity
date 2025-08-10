package org.example.arccoscalculator.gui;

import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

@DisabledIfEnvironmentVariable(named = "GITHUB_ACTIONS", matches = "true")
class ArccosApplicationTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new ArccosApplication().start(stage);
    }

    @Test
    void testWindowTitleAndUIElements() throws Exception {
        Stage stage = FxToolkit.registerPrimaryStage();
        assertEquals("Arccos Calculator", stage.getTitle());

        TextField inputField = lookup("#inputField").query();
        Button calcBtn = lookup("#calcBtn").query();
        Label resultLabel = lookup("#resultLabel").query();
        Label messageLabel = lookup("#messageLabel").query();

        assertNotNull(inputField);
        assertNotNull(calcBtn);
        assertNotNull(resultLabel);
        assertNotNull(messageLabel);
    }
}