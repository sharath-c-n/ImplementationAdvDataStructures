import java.util.Map;
import java.util.Stack;

/**
 * This class evaluates the postfix expression and sends the result back to the caller
 */
public class PostfixEvaluator {

    /**
     * This function is called to evaluate a postfix expression
     *
     * @param valueMap   : a table to store the values of a variable
     * @param postfixExp : expressed to be evaluated
     * @return : value of the expression
     * @IllegalArgumentException : thrown when the value table doesnt have the symbol that is referred in the
     * expression.
     */
    public static Num evaluator(Map<Character, Num> valueMap, String postfixExp) {
        Stack<Character> stack = new Stack<Character>();
        char keyValue = 130;
        for (int i = 0; i < postfixExp.length(); i++) {
            char cur = postfixExp.charAt(i);
            if (!Operators.isOperator(cur)) {
                stack.push(cur);
            } else {
                Num right = valueMap.get(stack.pop());
                if (right == null) {
                    throw new IllegalArgumentException("Argument not defined in expression : " + postfixExp);
                }
                Num left = null;
                if (Operators.getByValue(cur).isBinary()) {
                    char key = stack.pop();
                    left = valueMap.get(key);
                    if (left == null) {
                        throw new IllegalArgumentException("Argument not defined in expression : " + postfixExp);
                    }
                    if (isTemp(key)) {
                        keyValue--;
                        valueMap.remove(key);
                    }
                }
                Num temp = operate(left, right, Operators.getByValue(cur));
                valueMap.put(keyValue, temp);
                stack.push(keyValue++);
            }
        }
        Num result = valueMap.get(stack.pop());
        valueMap.remove(--keyValue);
        return result;

    }

    private static boolean isTemp(char key) {
        return key >= 130;
    }

    public static Num operate(Num left, Num right, Operators operator) {
        Num temp = null;
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
