package cs6301.g26;


import java.util.*;

public class ShuntingYard {

    public static String parse(String inputString) {
        StringBuilder output = new StringBuilder();
        if (inputString != null && inputString.length() > 0) {
            Stack<Character> operators = new Stack<Character>();
            for (int i = 0; i < inputString.length(); i++) {
                char currentChar = inputString.charAt(i);
                if (Operators.isOperator(currentChar)) {
                    if (!operators.isEmpty()) {
                        char stackTop = operators.peek();
                        while (!operators.isEmpty() && Operators.isOperator(stackTop) &&
                                Operators.compare(Operators.getByValue(currentChar),Operators.getByValue(stackTop)) <= 0
                                && Operators.getByValue(stackTop).isLeftAssociated()) {
                                output.append(operators.pop());
                                stackTop = operators.peek();
                        }
                    }
                    operators.push(currentChar);
                } else if (currentChar == '(') {
                    operators.push(currentChar);
                } else if (currentChar == ')') {
                    char stackTop = operators.pop();
                    while (stackTop != '(') {
                        output.append(stackTop);
                        stackTop = operators.pop();
                    }
                } else {
                    output.append(currentChar);
                }
            }
            while (!operators.isEmpty()) {
                output.append(operators.pop());
            }
        }
        return output.toString();
    }


    public static void main(String[] args) {
        System.out.println(ShuntingYard.parse("3+4*2/(1-5)^2^3".replaceAll("\\s","")));
    }
}
