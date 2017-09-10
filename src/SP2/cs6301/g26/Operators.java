package cs6301.g26;

/**
 * This is a helper emun for shunting yard implementation
 * @author Sharath
 */

import java.util.HashMap;
import java.util.Map;

public enum Operators {
    ADD('+', 2, true),
    SUBTRACT('-', 2, true),
    MULTIPLY('*', 3, true),
    DIVIDE('/', 3, true),
    POWER('^', 4, false),
    FACTORIAL('!', 4, false);

    private static final Map<Character, Operators> operatorMap = new HashMap<>(values().length, 1);

    static {
        for (Operators operators : values()) operatorMap.put(operators.value, operators);
    }

    private char value;
    private int precedence;
    private boolean isLeftAssociated;

    Operators(char value, int precedence, boolean isLeftAssociated) {
        this.value = value;
        this.precedence = precedence;
        this.isLeftAssociated = isLeftAssociated;
    }

    public static boolean isOperator(char input){
        return  operatorMap.containsKey(input);
    }

    public char getValue() {
        return value;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftAssociated() {
        return isLeftAssociated;
    }

    public static Operators getByValue(char c){
        return operatorMap.get(c);
    }
    public static int compare(Operators op1, Operators op2){
        return op1.getPrecedence() - op2.getPrecedence();
    }

}
