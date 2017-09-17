package cs6301.g26;

import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * This class evaluates the expression expression and sends the result back to the caller
 */
public class PostfixEvaluatorL4 extends PostfixEvaluator{
    /**
     * This function is called to evaluate a expression expression
     * @param valueMap   : a table to store the values of a variable
     * @param postfixExp : expressed to be evaluated
     * @return : value of the expression
     * @exception IllegalArgumentException : thrown when the value table doesnt have the symbol that is referred in the
     * expression.
     */
    public static Num evaluate(Map<String, Num> valueMap, Queue<String> postfixExp) {
        Stack<Num> stack = new Stack<>();
        for (String cur:postfixExp) {
            if (!Operators.isOperator(cur.charAt(0))) {
                Num value = valueMap.get(cur);
                if(value == null){
                    throw new IllegalArgumentException("Argument not defined in expression : " + postfixExp);
                }
                stack.push(value);
            } else {
                Num right = stack.pop();
                Operators operators = Operators.getByValue(cur.charAt(0));
                Num left = null;
                if (operators.isBinary()) {
                    left = stack.pop();
                }
                stack.push(operate(left, right, operators));
            }
        }
        return stack.pop();
    }
}
