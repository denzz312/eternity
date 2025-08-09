module org.example.arccoscalculator {
  requires javafx.controls;
  requires javafx.fxml;
  requires info.picocli;
  requires java.logging;

  exports org.example.arccoscalculator.gui;

  opens org.example.arccoscalculator.gui to
      javafx.fxml;
  opens org.example.arccoscalculator.cli to
      info.picocli;
}
