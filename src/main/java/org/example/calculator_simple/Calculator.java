package org.example.calculator_simple;

public class Calculator {
    public int addition(int a, int b)   {
        try{
            return a + b;
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Exception occurred: " + e.getMessage());
        }
        return 0; // Return a default value or handle it as needed
    }

    // Method for multiplication
    public int multiplication(int a, int b)  throws ArithmeticException {
        try {
            return a * b;
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Exception occurred: " + e.getMessage());

        }
        return 0; // Return a default value or handle it as needed
    }
    // Method for division
    public int division(int a, int b) throws ArithmeticException {
        try {
            if (b == 0) {
                throw new ArithmeticException("Division by zero is not allowed.");
            }
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Exception occurred: " + e.getMessage());
            return 0;
        }
    }
    // Method for subtraction
    public int subtraction(int a, int b) throws ArithmeticException {
        try {
            return a - b;
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic Exception occurred: " + e.getMessage());
           return 0;
        }
    }
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

            int a = 10;
            int b = 0; // Change this value to test different scenarios
            System.out.println("Addition: " + calculator.addition(a, b));
            System.out.println("Multiplication: " + calculator.multiplication(a, b));
            System.out.println("Division: " + calculator.division(a, b));
            System.out.println("Subtraction: " + calculator.subtraction(a, b));

    }
}
