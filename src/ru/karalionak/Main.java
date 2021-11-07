package ru.karalionak;

import java.util.List;
import java.util.Scanner;

public class Main {

    // 7 + 5 * ( - 9 + 8 ) + 9 * 5 %
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter an expression separated by a space:");
        String expression = scanner.nextLine();

        PostNotationParser parser = PostNotationParser.getInstance();
        List<String> postfix = parser.infixToPostfix(expression);
        System.out.println("Postfix expression form:" + postfix);

        PostNotationService service = PostNotationService.getInstance();
        double result = service.computing(postfix);
        System.out.println("Result:" + result);
    }
}
