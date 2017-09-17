package cs6301.g26;

import java.util.Map;
import java.util.Stack;

/**
 * This class evaluates the expression expression and sends the result back to the caller
 */
public class PostfixEvaluator {
        /**
     * This function is called to evaluate a expression expression
     * @param valueMap   : a table to store the values of a variable
     * @param postfixExp : expressed to be evaluated
     * @return : value of the expression
     * @exception IllegalArgumentException : thrown when the value table doesnt have the symbol that is referred in the
     * expression.
     */
    public static Num evaluate(Map<Character, Num> valueMap, String postfixExp) {
        Stack<Num> stack = new Stack<>();
        for (int i = 0; i < postfixExp.length(); i++) {
            char cur = postfixExp.charAt(i);
            if (!Operators.isOperator(cur)) {
                Num value = valueMap.get(cur);
                if (value == null) {
                    throw new IllegalArgumentException("Argument not defined in expression : " + postfixExp);
                }
                stack.push(value);
            } else {
                Num right = stack.pop();
                Operators operators = Operators.getByValue(cur);
                Num left = null;
                if (operators.isBinary()) {
                    left = stack.pop();
                }
                stack.push(operate(left, right, operators));
            }
        }
        return stack.pop();

    }

    /**
     * Performs the specified operation on the operands provided
     * @param left : left operand
     * @param right : right operand
     * @param operator : operator
     * @return : result of the operation
     */
    protected static Num operate(Num left, Num right, Operators operator) {
        Num temp ;
        switch (operator) {
            case ADD:
                temp = Num.add(left, right);
                break;
            case SUBTRACT:
                temp = Num.subtract(left, right);
                break;
            case DIVIDE:
                temp = Num.divide(left, right);
                break;
            case MULTIPLY:
                temp = Num.product(left, right);
                break;
            case MOD:
                temp = Num.mod(left, right);
                break;
            case POWER:
                temp = Num.power(left, right);
                break;
            case SQRT:
                temp = Num.squareRoot(right);
                break;
            default:
                temp = null;
                break;
        }
        return temp;
    }


}
