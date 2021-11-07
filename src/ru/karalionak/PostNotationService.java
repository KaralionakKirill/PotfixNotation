package ru.karalionak;

import java.util.List;
import java.util.Stack;

public class PostNotationService {
    private final Stack<Double> operands = new Stack<>();
    private static PostNotationService instance;

    private PostNotationService() {

    }

    public static PostNotationService getInstance() {
        if (instance == null) {
            instance = new PostNotationService();
        }
        return instance;
    }

    public double computing(List<String> postfixForm) {
        for (String token : postfixForm) {
            if (token.matches("\\d+(\\.\\d+)?")) {
                operands.push(Double.parseDouble(token));
            } else {
                intermediateComputing(token);
            }
        }
        return operands.pop();
    }

    private void intermediateComputing(String operator) {
        switch (operator) {
            case "%" -> {
                double number = operands.pop();
                double result = number / 100;
                operands.push(result);
            }
            case "*" -> {
                double secondNumber = operands.pop();
                double firstNumber = operands.pop();
                double result = firstNumber * secondNumber;
                operands.push(result);
            }
            case "/" -> {
                double secondNumber = operands.pop();
                double firstNumber = operands.pop();
                double result = firstNumber / secondNumber;
                operands.push(result);
            }
            case "+" -> {
                double secondNumber = operands.pop();
                double firstNumber = operands.pop();
                double result = firstNumber + secondNumber;
                operands.push(result);
            }
            case "!" -> {
                double number = operands.pop();
                number *= -1;
                operands.push(number);
            }
            case "-" -> {
                double secondNumber = operands.pop();
                double firstNumber = operands.pop();
                double result = firstNumber - secondNumber;
                operands.push(result);
            }
            case "^" -> {
                double secondNumber = operands.pop();
                double firstNumber = operands.pop();
                double result = Math.pow(firstNumber, secondNumber);
                operands.push(result);
            }
            default -> throw new IllegalArgumentException(operator + " - this operator does not exist ");
        }
    }
}
