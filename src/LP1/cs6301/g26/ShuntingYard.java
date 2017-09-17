package cs6301.g26;

import cs6301.g26.Operators;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * This class provides a static method to parse an Infix expression and return the corresponding
 * expression expression. This uses cs6301.g26.ShuntingYard algorithm.
 * Source : https://en.wikipedia.org/wiki/Shunting-yard_algorithm
 *
 * @author Sharath
 */
public class ShuntingYard {
    /**
     * This will parse the input infix expression and produces the corresponding expression expression
     *
     * @param infixExp : infix expression
     * @return : expression expression
     */
    public static Queue<String> parse(Queue<String> infixExp) {
        Queue<String> output = new ArrayDeque<>();
        Stack<String> operators = new Stack<String>();
        //Loop through every character in the input string
        for (String currentChar : infixExp) {
            if (Operators.isOperator(currentChar.charAt(0))) {
                //Check if the stack is empty
                if (!operators.isEmpty()) {
                    String stackTop = operators.peek();
                    //Check if the current operator has higher priority than the operator on the stack
                    while (!operators.isEmpty() && Operators.isOperator(stackTop.charAt(0)) &&
                            Operators.compare(Operators.getByValue(currentChar.charAt(0)), Operators.getByValue(stackTop.charAt(0))) <= 0
                            && Operators.getByValue(stackTop.charAt(0)).isLeftAssociated()) {
                        output.add(operators.pop());
                        stackTop = operators.peek();
                    }
                }
                operators.push(currentChar);
            } else if (currentChar.equals("(")) {
                operators.push(currentChar);
            } else if (currentChar.equals(")")) {
                String stackTop = operators.pop();
                while (!stackTop.equals("(")) {
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
        return output;
    }
}

