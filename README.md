# ArccosCalculator

A Java application for calculating arccosine values, featuring both a command-line interface (CLI) and a JavaFX graphical user interface (GUI).

## Features

- Command-line interface (CLI) using Picocli
- Graphical user interface (GUI) using JavaFX
- Unit tests with JUnit 5
- Code coverage reports with JaCoCo

## Requirements

- Java 21
- Maven 3.6 or newer

## Commands

To build the project and create the executable JAR:

```sh
mvn clean install
```
To run the CLI application:

```sh
java -jar target/ArccosCalculator-1.1.3.jar 
```

To launch the GUI application:

```sh
mvn clean javafx:run
```
