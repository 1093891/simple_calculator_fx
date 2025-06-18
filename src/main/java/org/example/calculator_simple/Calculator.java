package org.example.calculator_simple;

public class Calculator {

    /**
     * Performs addition of two double numbers.
     * @param a The first operand.
     * @param b The second operand.
     * @return The sum of a and b.
     */
    public double addition(double a, double b) {
        return a + b;
    }

    /**
     * Performs multiplication of two double numbers.
     * @param a The first operand.
     * @param b The second operand.
     * @return The product of a and b.
     */
    public double multiplication(double a, double b) {
        return a * b;
    }

    /**
     * Performs division of two double numbers.
     * @param a The dividend.
     * @param b The divisor.
     * @return The quotient of a and b.
     * @throws ArithmeticException if division by zero is attempted.
     */
    public double division(double a, double b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    /**
     * Performs subtraction of two double numbers.
     * @param a The first operand.
     * @param b The second operand.
     * @return The difference between a and b.
     */
    public double subtraction(double a, double b) {
        return a - b;
    }

    /**
     * Evaluates a simple arithmetic expression string.
     * This implementation handles single operations at a time (e.g., "10+5", "20/4").
     * For complex expressions with operator precedence (e.g., "2+3*4"),
     * a more advanced parsing technique like Shunting-yard algorithm would be required.
     *
     * @param expression The arithmetic expression as a string (e.g., "10+5", "15-3", "20*2", "10/0").
     * @return The result of the evaluation.
     * @throws IllegalArgumentException if the expression format is invalid.
     * @throws ArithmeticException if a division by zero occurs.
     */
    public double evaluate(String expression) throws IllegalArgumentException, ArithmeticException {
        // Remove any spaces from the expression
        expression = expression.replaceAll(" ", "");

        // Find the operator
        char operator = ' ';
        int operatorIndex = -1;

        // Iterate through common operators to find the first one
        // This simple approach assumes one operator per calculation for basic evaluation
        // and doesn't handle operator precedence or multiple chained operations like "1+2-3"
        // in a single call. For full calculator logic, you'd extend this significantly.
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                operator = c;
                operatorIndex = i;
                break; // Found the first operator
            }
        }

        if (operatorIndex == -1) {
            // If no operator is found, it might just be a number
            try {
                return Double.parseDouble(expression);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid expression format: " + expression, e);
            }
        }

        // Split the expression into two operands
        String operand1Str = expression.substring(0, operatorIndex);
        String operand2Str = expression.substring(operatorIndex + 1);

        double operand1;
        double operand2;

        try {
            operand1 = Double.parseDouble(operand1Str);
            operand2 = Double.parseDouble(operand2Str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in expression: " + expression, e);
        }

        // Perform the operation
        switch (operator) {
            case '+':
                return addition(operand1, operand2);
            case '-':
                return subtraction(operand1, operand2);
            case '*':
                return multiplication(operand1, operand2);
            case '/':
                return division(operand1, operand2);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        // Test individual methods with doubles
        double val1 = 10.5;
        double val2 = 2.0;
        double val3 = 0.0;

        System.out.println("--- Individual Method Tests ---");
        System.out.println("Addition: " + calculator.addition(val1, val2));         // Expected: 12.5
        System.out.println("Multiplication: " + calculator.multiplication(val1, val2)); // Expected: 21.0
        System.out.println("Subtraction: " + calculator.subtraction(val1, val2));    // Expected: 8.5
        try {
            System.out.println("Division (10.5 / 2.0): " + calculator.division(val1, val2)); // Expected: 5.25
        } catch (ArithmeticException e) {
            System.out.println("Division Error: " + e.getMessage());
        }
        try {
            System.out.println("Division (10.5 / 0.0): " + calculator.division(val1, val3)); // Expected: Error
        } catch (ArithmeticException e) {
            System.out.println("Division Error: " + e.getMessage()); // Should print "Division by zero is not allowed."
        }


        // Test the evaluate method
        System.out.println("\n--- Evaluate Method Tests ---");
        String[] expressions = {
                "10+5",      // Simple addition
                "15-3",      // Simple subtraction
                "20*2.5",    // Simple multiplication with decimal
                "10/4",      // Simple division
                "10/0",      // Division by zero
                "7",         // Just a number
                "3.14",      // Just a decimal number
                "abc+def",   // Invalid operands
                "10%5"       // Unsupported operator (for this basic implementation)
        };

        for (String expr : expressions) {
            try {
                double result = calculator.evaluate(expr);
                System.out.println("Expression \"" + expr + "\" = " + result);
            } catch (IllegalArgumentException | ArithmeticException e) {
                System.out.println("Error evaluating \"" + expr + "\": " + e.getMessage());
            }
        }
    }
}