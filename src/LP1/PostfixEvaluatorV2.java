import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * This class evaluates the expression expression and sends the result back to the caller
 */
public class PostfixEvaluatorV2 {
    /**
     * This function is called to evaluate a expression expression
     * @param valueMap   : a table to store the values of a variable
     * @param postfixExp : expressed to be evaluated
     * @return : value of the expression
     * @exception IllegalArgumentException : thrown when the value table doesnt have the symbol that is referred in the
     * expression.
     */
    public static Num evaluator(Map<String, Num> valueMap, Queue<String> postfixExp) {
        Stack<String> stack = new Stack<>();
        char intermediateRes = 1;
        for (String cur:postfixExp) {
            if (!Operators.isOperator(cur.charAt(0))) {
                stack.push(cur);
            } else {
                Num right = valueMap.get(stack.pop());
                Operators operators = Operators.getByValue(cur.charAt(0));
                if (right == null) {
                    throw new IllegalArgumentException("Argument not defined in expression : " + postfixExp);
                }
                Num left = null;
                if (operators.isBinary()) {
                    String key = stack.pop();
                    left = valueMap.get(key);
                    if (left == null) {
                        throw new IllegalArgumentException("Argument not defined in expression : " + postfixExp);
                    }
                    /*Remove the (key,value) pair from map if this was an intermediate result stored
                    * by the evaluator*/
                    if (isTemp(key)) {
                        valueMap.remove(key);
                        //Make the key available for reuse
                        intermediateRes--;
                    }
                }
                Num temp = operate(left, right, operators);
                valueMap.put("$"+intermediateRes, temp);
                stack.push("$"+intermediateRes);
            }
        }
        Num result = valueMap.get(stack.pop());
        valueMap.remove("$"+--intermediateRes);
        return result;

    }

    /**
     * Checks if the variable name passed is an intermediate variable, that was added by the evaluator
     * @param resultKey : key to be checked
     * @return : true if this key was added by the evaluator
     */
    private static boolean isTemp(String resultKey) {
        return resultKey.startsWith("$");
    }

    /**
     * Performs the specified operation on the operands provided
     * @param left : left operand
     * @param right : right operand
     * @param operator : operator
     * @return : result of the operation
     */
    private static Num operate(Num left, Num right, Operators operator) {
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
