import java.io.File;
import java.util.*;

public class L4Driver {
    private Map<Integer, Integer> labelMap;
    private Map<String, Num> variableMap;
    private List<LabelEntry> labelEntries;

    public enum Token {VAR, NUM, OP, EQ, EOL, QUES, COL}

    private static class LabelEntry {
        Queue<String> expression;
        String variable;
        boolean isLabel;


        public LabelEntry(Queue<String> expression, String variable,boolean isLabel) {
            this.expression = expression;
            this.variable = variable;
            this.isLabel = isLabel;
        }
    }

    public L4Driver() {
        labelMap = new HashMap<>();
        variableMap = new HashMap<>();
        labelEntries = new ArrayList<>();
    }

    public void runStateMachine(Scanner in) throws Exception {
        int constantsCount = 0;
        String variable = "";
        Queue<String> expression = new ArrayDeque<>();
        boolean isLabel = false;
        int label = -1, state = 0;
        while (in.hasNext()) {
            String symbol = in.next();
            Token token = tokenize(symbol);
            if (state == 0 && Token.VAR == token) {
                state = 2;
                variable = symbol;
                isLabel = false;
            } else if (state == 0 && Token.NUM == token) {
                state++;
                label = Integer.parseInt(symbol);
                isLabel = true;
            } else if (state == 1 && Token.VAR == token) {
                variable = symbol;
                state++;
            } else if (state == 2 && Token.EQ == token) {
                state++;
            } else if (state == 2 && Token.EOL == token) {
                System.out.println(variableMap.get(variable));
                expression.add(variable);
                addLabelEntry(variable, expression, label, isLabel);
                expression.clear();
                state = 0;
            } else if (state == 2 && Token.QUES == token && isLabel) {
                state = 6;
                expression.add(variable);
                expression.add(symbol);
            } else if (state == 3 && Token.NUM == token) {
                variableMap.put(variable, new Num(symbol));
                if (isLabel)
                    expression.add(symbol);
                state++;
            } else if (state == 3 && Token.VAR == token) {
                state = 5;
                expression.add(symbol);
            } else if (state == 4 && Token.EOL == token) {
                addLabelEntry(variable, expression, label, isLabel);
                System.out.println(variableMap.get(variable));
                expression.clear();
                isLabel = false;
                state = 0;
            } else if (state == 5 && (Token.VAR == token || Token.OP == token)) {
                expression.add(symbol);
            } else if (state == 5 && Token.NUM == token) {
                variableMap.put("t" + constantsCount, new Num(symbol));
                expression.add("t" + constantsCount++);
            } else if (state == 5 && Token.EOL == token) {
                if (isLabel) {
                    expression = ShuntingYard.parse(expression);
                }
                addLabelEntry(variable, expression, label, isLabel);
                variableMap.put(variable, PostfixEvaluatorV2.evaluate(variableMap, expression));
                if(!isLabel)
                    System.out.println(variableMap.get(variable));
                expression.clear();
                isLabel = false;
                state = 0;
            } else if ((state == 6 || state == 8) && Token.NUM == token) {
                state++;
                expression.add(symbol);
            } else if (state == 7 && Token.COL == token) {
                state++;
                expression.add(symbol);
            } else if ((state == 7 || state == 9) && Token.EOL == token) {
                addLabelEntry(null, expression, label, isLabel);
                executeGoto(expression);
                isLabel = false;
                expression.clear();
                state = 0;
            } else if (state == 0 && Token.EOL == token) {
                variableMap.get(variable).printList();
                break;
            } else {
                throw new IllegalStateException("Fatal error cannot continue!!");
            }
        }
    }

    private Token tokenize(String s) throws Exception {
        if (s.matches("\\d+")) {  // one or more digits
            return Token.NUM;
        } else if (s.matches("[a-z]")) {  // letter
            return Token.VAR;
        } else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%") || s.equals("^") || s.equals("|")) {
            return Token.OP;
        } else if (s.equals("=")) {
            return Token.EQ;
        } else if (s.equals(";")) {
            return Token.EOL;
        } else if (s.equals("?")) {
            return Token.QUES;
        } else if (s.equals(":")) {
            return Token.COL;
        } else {  // Error
            throw new Exception("Unknown token: " + s);
        }
    }

    private void executeGoto(Queue<String> expression) {
        int label = getBranchValue(expression, variableMap.get(expression.peek()));
        if (label != -1) {
            executeFromTable(label);
        }
    }

    private void addLabelEntry(String var, Queue<String> expression, int label, boolean isLabel) {
        labelEntries.add(new LabelEntry(new ArrayDeque<>(expression), var,isLabel));
        if(isLabel)
            labelMap.put(label,labelEntries.size()-1 );
    }

    private void executeFromTable(int branchTo) {
        while (branchTo != -1 && labelEntries.size() > branchTo) {
            LabelEntry entry = labelEntries.get(branchTo);
            if (entry.variable != null) {
                variableMap.put(entry.variable, PostfixEvaluatorV2.evaluate(variableMap, entry.expression));
                if(!entry.isLabel){
                    System.out.println(variableMap.get(entry.variable));
                }
                branchTo++;
            } else {
                branchTo = getBranchValue(entry.expression, variableMap.get(entry.expression.peek()));
            }
        }
    }

    private int getBranchValue(Queue<String> infixExp, Num num) {
        Iterator<String> iterator = infixExp.iterator();
        String branchTo = "";
        if (num.isZero()) {
            if (infixExp.contains(":")) {
                while (iterator.hasNext()) {
                    branchTo = iterator.next();
                }
                return labelMap.get(Integer.parseInt(branchTo));
            }
        } else {
            while (iterator.hasNext() && !branchTo.equals("?")) {
                branchTo = iterator.next();
            }
            return labelMap.get(Integer.parseInt(iterator.next()));
        }
        return -1;
    }

    /**
     * This function uses state machine to evaluate the input string
     * State 0 : Is the starting point of SM and expects a variable or a semicolon
     * State 1 : Expects an equals symbol
     * State 2 : The next token could be either a Number or a variable, if a varable is received
     * the state will not be changed untill we receive a semicolon at which point the state
     * is reset to 0.
     * State 3 : if we receive a number in state 3 we will go to state 3 and this expects the next token to be
     * a semicolon.
     *
     * @param args : not used
     * @throws Exception : if the input statement is wrong, illegal state exception is thrown.
     */

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("./src/LP1/lp1-l4-in2.txt"));
        L4Driver l4Driver = new L4Driver();
        l4Driver.runStateMachine(in);
    }

}
