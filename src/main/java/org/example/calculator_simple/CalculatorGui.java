package org.example.calculator_simple;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CalculatorGui extends Application {
    BorderPane pane;
    Calculator calculator; // Assuming you have a Calculator class for logic

    public CalculatorGui() {
        pane = new BorderPane();
        calculator = new Calculator(); // Initialize your Calculator logic class
        init();
    }

    public void init() {
        pane.setPrefSize(400, 600);
        pane.setStyle("-fx-background-color: #646464;");

        Label title = new Label("Calculator");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: 700;");
        pane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        title.setPadding(new Insets(10));

        VBox ViewPane = new VBox(10); // Added spacing between elements
        ViewPane.setStyle("-fx-background-color: #ffffff;");
        ViewPane.setPrefSize(400, 500);
        pane.setCenter(ViewPane);
        BorderPane.setMargin(ViewPane, new Insets(10));
        BorderPane.setAlignment(ViewPane, Pos.CENTER);

        // Display Area
        HBox displayBox = new HBox(); // Use HBox to potentially center the TextField better
        displayBox.setAlignment(Pos.CENTER);
        displayBox.setPadding(new Insets(20));
        displayBox.setStyle("-fx-background-color: rgba(78,71,71,0.56); -fx-border-color: #cccccc; -fx-border-width: 1px;");
        displayBox.setPrefSize(380, 100);

        TextField displayLabel = new TextField("0");
        displayLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: #000000; -fx-background-color: transparent; -fx-border-width: 0;");
        displayLabel.setAlignment(Pos.CENTER_RIGHT); // Align text to the right
        displayLabel.setEditable(false); // Make display non-editable by user
        displayBox.getChildren().add(displayLabel);
        ViewPane.getChildren().add(displayBox);

        // Buttons Grid
        GridPane buttonsPane = createButtonsGrid(displayLabel);
        ViewPane.getChildren().add(buttonsPane);
        VBox.setVgrow(buttonsPane, Priority.ALWAYS); // Make buttonsPane grow to fill available space
    }

    private GridPane createButtonsGrid(TextField displayLabel) {
        GridPane buttonsPane = new GridPane();
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setHgap(10);
        buttonsPane.setVgap(10);
        buttonsPane.setPadding(new Insets(10));

        // Define button styles for reuse
        String numButtonStyle = "-fx-font-size: 24px; -fx-background-color: #e0e0e0; -fx-text-fill: #000000; -fx-min-width: 70px; -fx-min-height: 70px;";
        String opButtonStyle = "-fx-font-size: 24px; -fx-background-color: #ffa500; -fx-text-fill: #ffffff; -fx-min-width: 70px; -fx-min-height: 70px;";
        String clearButtonStyle = "-fx-font-size: 24px; -fx-background-color: #d32f2f; -fx-text-fill: #ffffff; -fx-min-width: 70px; -fx-min-height: 70px;";
        String equalsButtonStyle = "-fx-font-size: 24px; -fx-background-color: #4CAF50; -fx-text-fill: #ffffff; -fx-min-width: 70px; -fx-min-height: 70px;";
        String miscButtonStyle = "-fx-font-size: 24px; -fx-background-color: #cccccc; -fx-text-fill: #000000; -fx-min-width: 70px; -fx-min-height: 70px;";


        // Row 0: Clear, Back, Percent, Divide
        Button clearButton = createButton("C", clearButtonStyle, e -> displayLabel.setText("0")); // Changed to 'C' for clear
        buttonsPane.add(clearButton, 0, 0);

        Button allClearButton = createButton("AC", clearButtonStyle, e -> { // All Clear
            displayLabel.setText("0");
            // You might also reset calculator internal state here
        });
        buttonsPane.add(allClearButton, 1, 0);


        Button backButton = createButton("←", miscButtonStyle, e -> {
            String currentText = displayLabel.getText();
            if (currentText.length() > 1 && !currentText.equals("Error")) {
                displayLabel.setText(currentText.substring(0, currentText.length() - 1));
            } else {
                displayLabel.setText("0");
            }
        });
        buttonsPane.add(backButton, 2, 0);

        Button divideButton = createButton("÷", opButtonStyle, e -> appendToDisplay(displayLabel, "/"));
        buttonsPane.add(divideButton, 3, 0);

        // Row 1: 7, 8, 9, Multiply
        Button button7 = createButton("7", numButtonStyle, e -> appendDigit(displayLabel, "7"));
        buttonsPane.add(button7, 0, 1);
        Button button8 = createButton("8", numButtonStyle, e -> appendDigit(displayLabel, "8"));
        buttonsPane.add(button8, 1, 1);
        Button button9 = createButton("9", numButtonStyle, e -> appendDigit(displayLabel, "9"));
        buttonsPane.add(button9, 2, 1);
        Button multiplyButton = createButton("×", opButtonStyle, e -> appendToDisplay(displayLabel, "*"));
        buttonsPane.add(multiplyButton, 3, 1);

        // Row 2: 4, 5, 6, Subtract
        Button button4 = createButton("4", numButtonStyle, e -> appendDigit(displayLabel, "4"));
        buttonsPane.add(button4, 0, 2);
        Button button5 = createButton("5", numButtonStyle, e -> appendDigit(displayLabel, "5"));
        buttonsPane.add(button5, 1, 2);
        Button button6 = createButton("6", numButtonStyle, e -> appendDigit(displayLabel, "6"));
        buttonsPane.add(button6, 2, 2);
        Button subtractButton = createButton("-", opButtonStyle, e -> appendToDisplay(displayLabel, "-"));
        buttonsPane.add(subtractButton, 3, 2);

        // Row 3: 1, 2, 3, Add
        Button button1 = createButton("1", numButtonStyle, e -> appendDigit(displayLabel, "1"));
        buttonsPane.add(button1, 0, 3);
        Button button2 = createButton("2", numButtonStyle, e -> appendDigit(displayLabel, "2"));
        buttonsPane.add(button2, 1, 3);
        Button button3 = createButton("3", numButtonStyle, e -> appendDigit(displayLabel, "3"));
        buttonsPane.add(button3, 2, 3);
        Button addButton = createButton("+", opButtonStyle, e -> appendToDisplay(displayLabel, "+"));
        buttonsPane.add(addButton, 3, 3);

        // Row 4: Sign change, 0, Decimal, Equals
        Button signButton = createButton("+/-", miscButtonStyle, e -> {
            String currentText = displayLabel.getText();
            try {
                double value = Double.parseDouble(currentText);
                displayLabel.setText(String.valueOf(-value));
            } catch (NumberFormatException ex) {
                // Do nothing or show error if current text is not a valid number
            }
        });
        buttonsPane.add(signButton, 0, 4);

        Button button0 = createButton("0", numButtonStyle, e -> appendDigit(displayLabel, "0"));
        buttonsPane.add(button0, 1, 4);

        Button decimalButton = createButton(".", miscButtonStyle, e -> {
            String currentText = displayLabel.getText();
            // Only add decimal if it's not already present in the current number
            if (!currentText.contains(".") && !currentText.equals("Error")) {
                displayLabel.setText(currentText + ".");
            }
        });
        buttonsPane.add(decimalButton, 2, 4);

        Button equalsButton = createButton("=", equalsButtonStyle, e -> {
            // This is where you would call your Calculator logic to evaluate the expression
            String expression = displayLabel.getText();
            try {
                double result = calculator.evaluate(expression); // Assuming calculator has an evaluate method
                displayLabel.setText(String.valueOf(result));
            } catch (Exception ex) { // Catch more specific exceptions from your calculator logic
                displayLabel.setText("Error");
                System.err.println("Calculation error: " + ex.getMessage());
            }
        });
        buttonsPane.add(equalsButton, 3, 4);

        // Optional Row for advanced functions (e.g., Square Root, Percent)
        Button sqrtButton = createButton("√", miscButtonStyle, e -> {
            String currentText = displayLabel.getText();
            try {
                double value = Double.parseDouble(currentText);
                if (value >= 0) {
                    displayLabel.setText(String.valueOf(Math.sqrt(value)));
                } else {
                    displayLabel.setText("Error"); // Cannot take square root of negative number
                }
            } catch (NumberFormatException ex) {
                displayLabel.setText("Error");
            }
        });
        buttonsPane.add(sqrtButton, 0, 5);

        Button percentButton = createButton("%", miscButtonStyle, e -> {
            // This usually divides the current number by 100
            String currentText = displayLabel.getText();
            try {
                double value = Double.parseDouble(currentText);
                displayLabel.setText(String.valueOf(value / 100));
            } catch (NumberFormatException ex) {
                displayLabel.setText("Error");
            }
        });
        buttonsPane.add(percentButton, 1, 5);


        // Set column constraints for uniform button width
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setHgrow(Priority.ALWAYS);
        colConstraints.setPercentWidth(25); // Each column takes 25% of the width for 4 columns
        buttonsPane.getColumnConstraints().addAll(colConstraints, colConstraints, colConstraints, colConstraints);

        // Set row constraints for uniform button height
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        rowConstraints.setPercentHeight(20); // Each row takes 20% of the height for 5 rows
        buttonsPane.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints, rowConstraints); // Adjust based on actual number of rows

        return buttonsPane;
    }

    // Helper method to create buttons with consistent styling and action
    private Button createButton(String text, String style, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Make button fill cell
        button.setOnAction(action);
        return button;
    }

    // Helper method to append digits to the display
    private void appendDigit(TextField displayLabel, String digit) {
        String currentText = displayLabel.getText();
        if (currentText.equals("0") || currentText.equals("Error")) { // If display is "0" or "Error", replace it
            displayLabel.setText(digit);
        } else {
            displayLabel.setText(currentText + digit);
        }
    }

    // Helper method to append operators to the display
    private void appendToDisplay(TextField displayLabel, String operator) {
        String currentText = displayLabel.getText();
        if (!currentText.equals("0") && !currentText.equals("Error")) {
            // Basic check to prevent multiple operators directly next to each other (can be improved)
            char lastChar = currentText.charAt(currentText.length() - 1);
            if (Character.isDigit(lastChar) || lastChar == '.') {
                displayLabel.setText(currentText + operator);
            }
        }
    }


    public BorderPane getPane() {
        return pane;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(getPane());
        primaryStage.setTitle("Simple Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}