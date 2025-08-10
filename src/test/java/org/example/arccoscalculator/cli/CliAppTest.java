package org.example.arccoscalculator.cli;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;
import picocli.CommandLine;

@Timeout(10)
class CliAppTest {

    private PrintStream originalOut;
    private InputStream originalIn;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        originalOut = System.out;
        originalIn = System.in;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() throws IOException {
        System.setOut(originalOut);
        System.setIn(originalIn);
        outContent.close();
    }

    // ---- Helpers ----

    private String getStdout() {
        return outContent.toString();
    }

    private void setStdin(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    // ---- Flag-mode tests ----

    @Test
    @DisplayName("Flag mode: -x valid prints formatted result and exits 0")
    void flagMode_validX() {
        int code = new CommandLine(new CliApp()).execute("-x", "0.5");
        String out = getStdout();

        assertEquals(0, code);
        assertTrue(out.contains("Arccos(0.5000)"), "Should print formatted input");
        assertTrue(out.contains("= 1.0472 radians"), "Should print arccos(0.5) ≈ 1.0472");
    }

    @Test
    @DisplayName("Flag mode: -x out-of-range prints validation error and exits 0")
    void flagMode_outOfRange() {
        int code = new CommandLine(new CliApp()).execute("-x", "1.5");
        String out = getStdout();

        assertEquals(0, code);
        assertTrue(out.contains("Value must be between -1 and 1"),
                "Should warn about range violation");
    }

    @Test
    @DisplayName("--help prints usage and exits 0")
    void helpPrintsUsage() {
        int code = new CommandLine(new CliApp()).execute("--help");
        String out = getStdout();

        assertEquals(0, code);
        assertTrue(out.contains("Computes arccos(x) in radians"), "Should include description");
        assertTrue(out.contains("-x"), "Should document -x option");
    }

    @Test
    @DisplayName("--version prints version and exits 0")
    void versionPrints() {
        int code = new CommandLine(new CliApp()).execute("--version");
        String out = getStdout();

        assertEquals(0, code);
        assertTrue(out.contains("Arccos Calculator 1.0"), "Should show version string");
    }

    // ---- Interactive-mode tests ----

    @Test
    @DisplayName("Interactive mode: valid number then 'exit' prints result and goodbye")
    void interactive_validThenExit() {
        setStdin("0.5\nexit\n");
        new CliApp().interactiveMode();

        String out = getStdout();
        assertTrue(out.contains("Welcome to the Arccos Calculator CLI"),
                "Should show welcome");
        assertTrue(out.contains("Arccos(0.5000) = 1.0472 radians"),
                "Should compute and print result");
        assertTrue(out.contains("Goodbye"), "Should say goodbye on exit");
    }

    @Test
    @DisplayName("Interactive mode: invalid input then valid number then 'exit'")
    void interactive_invalidThenValid() {
        setStdin("abc\n0.0\nexit\n");
        new CliApp().interactiveMode();

        String out = getStdout();
        assertTrue(out.contains("Invalid input"), "Should report invalid input");
        assertTrue(out.contains("Arccos(0.0000) = 1.5708 radians"),
                "Should compute arccos(0) ≈ 1.5708");
    }

    @Test
    @DisplayName("Interactive mode: out-of-range number shows range error then 'exit'")
    void interactive_outOfRange() {
        setStdin("1.2\nexit\n");
        new CliApp().interactiveMode();

        String out = getStdout();
        assertTrue(out.contains("Value must be between -1 and 1"),
                "Should show range error for out-of-range input");
    }
}
