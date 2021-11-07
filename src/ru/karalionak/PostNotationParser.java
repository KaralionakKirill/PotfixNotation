package ru.karalionak;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PostNotationParser {
    private static final String OPERATOR_REGEX = "[+\\-*/^%!]";
    private static PostNotationParser instance;
    private final Stack<String> operators = new Stack<>();
    private final List<String> postfixForm = new ArrayList<>();

    private PostNotationParser() {
    }

    public static PostNotationParser getInstance() {
        if (instance == null) {
            instance = new PostNotationParser();
        }
        return instance;
    }

    public List<String> infixToPostfix(String expression) {
        String[] tokens = expression.split("[ ]");
        replaceUnaryOperator(tokens);
        for (String token : tokens) {
            if (token.matches("\\d+(\\.\\d+)?")) {
                postfixForm.add(token);
            } else if (isOperator(token)) {
                addOperatorToStack(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    postfixForm.add(operators.pop());
                }
                operators.pop();
            }
        }
        while (!operators.empty()) {
            postfixForm.add(operators.pop());
        }
        return postfixForm;
    }

    private void replaceUnaryOperator(String[] tokens) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("-")) {
                if (i == 0) {
                    tokens[i] = "!";
                } else {
                    String prevToken = tokens[i - 1];
                    if (prevToken.equals("(")) {
                        tokens[i] = "!";
                    }
                }
            }
        }
    }

    private int receivePriority(String operator) {
        return switch (operator) {
            case "%", "*", "/" -> 3;
            case "+", "-", "!" -> 2;
            case "^" -> 1;
            default -> throw new IllegalArgumentException(operator + " - this operator does not exist ");
        };
    }

    private boolean isOperator(String token) {
        return token.matches(OPERATOR_REGEX);
    }

    private void addOperatorToStack(String token) {
        if ((operators.empty()) || (operators.peek().equals("("))) {
            operators.push(token);
        } else if (receivePriority(token) > (receivePriority(operators.peek()))) {
            operators.push(token);
        } else {
            while (!operators.empty()
                    && (!operators.peek().equals("("))
                    && (receivePriority(token) <= receivePriority(operators.peek()))) {

                postfixForm.add(operators.pop());
            }
            operators.push(token);
        }
    }
}
