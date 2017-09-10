package cs6301.g26;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * This class provides a static method to parse an Infix expression and return the corresponding
 * postfix expression. This uses ShuntingYard algorithm.
 * Source : https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 * @author Sharath
 */
public class ShuntingYard {
    /**
     * This will parse the input infix expression and produces the corresponding postfix expression
     * @param inputString : infix expression
     * @return : postfix expression
     */
    public static Queue<Character> parse(String inputString) {
        Queue<Character> output = new ArrayDeque<Character>();
        if (inputString != null && inputString.length() > 0) {
            //Stores the operators
            Stack<Character> operators = new Stack<Character>();
            //Loop through every character in the input string
            for (int i = 0; i < inputString.length(); i++) {
                char currentChar = inputString.charAt(i);
                if (Operators.isOperator(currentChar)) {
                    //Check if the stack is empty
                    if (!operators.isEmpty()) {
                        char stackTop = operators.peek();
                        //Check if the current operator has higher priority than the operator on the stack
                        while (!operators.isEmpty() && Operators.isOperator(stackTop) &&
                                Operators.compare(Operators.getByValue(currentChar),Operators.getByValue(stackTop)) <= 0
                                && Operators.getByValue(stackTop).isLeftAssociated()) {
                                output.add(operators.pop());
                                stackTop = operators.peek();
                        }
                    }
                    operators.push(currentChar);
                } else if (currentChar == '(') {
                    operators.push(currentChar);
                } else if (currentChar == ')') {
                    char stackTop = operators.pop();
                    while (stackTop != '(') {
                        output.add(stackTop);
                        stackTop = operators.pop();
                    }
                } else {
                    output.add(currentChar);
                }
            }
            //Append all the remaining operators to the output string
            while (!operators.isEmpty()) {
                output.add(operators.pop());
            }
        }
        return output;
    }


    public static void main(String[] args) {
        Queue<Character> output = ShuntingYard.parse("3+4*2/(1-5)^2^3".replaceAll("\\s",""));
        for(Character c : output){
            System.out.print(c);
        }
    }
}
