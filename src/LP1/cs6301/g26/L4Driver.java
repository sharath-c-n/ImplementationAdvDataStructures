package cs6301.g26;

import java.io.File;
import java.util.*;

public class L4Driver {
    private Map<Integer, Integer> labelMap;
    private Map<String, Num> variableMap;
    private List<LabelEntry> labelEntries;
    private int base = Num.defaultBase;

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

    public L4Driver(int base) {
        labelMap = new HashMap<>();
        variableMap = new HashMap<>();
        labelEntries = new ArrayList<>();
        this.base = base;
    }

    /**
     * This function uses state machine to evaluate the input string
     * State 0 : Is the starting point of SM and expects a variable or a semicolon
     * State 1 : Expects an equals symbol or a VAR
     * State 2 : The next token could be either a Number or a variable, if a variable is received
     * the state will not be changed until we receive a semicolon at which point the state
     * is reset to 0.
     * State 3 : if we receive a number in state 3 we will go to state 3 and this expects the next token to be
     * a semicolon.
     * State 4
     *
     * @throws Exception : if the input statement is wrong, illegal state exception is thrown.
     */
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
                expression.add(variable);
                addLabelEntry(variable, expression, label, isLabel);
                expression.clear();
                state = 0;
            } else if (state == 2 && Token.QUES == token && isLabel) {
                state = 6;
                expression.add(variable);
                expression.add(symbol);
            } else if (state == 3 && Token.NUM == token) {
                variableMap.put("t" + constantsCount, new Num(symbol,base));
                expression.add("t" + constantsCount++);
                state++;
            } else if (state == 3 && Token.VAR == token) {
                state = 5;
                expression.add(symbol);
            } else if (state == 4 && Token.EOL == token) {
                addLabelEntry(variable, expression, label, isLabel);
                expression.clear();
                isLabel = false;
                state = 0;
            } else if (state == 5 && (Token.VAR == token || Token.OP == token)) {
                expression.add(symbol);
            } else if (state == 5 && Token.NUM == token) {
                variableMap.put("t" + constantsCount, new Num(symbol,base));
                expression.add("t" + constantsCount++);
            } else if (state == 5 && Token.EOL == token) {
                if (isLabel) {
                    expression = ShuntingYard.parse(expression);
                }
                addLabelEntry(variable, expression, label, isLabel);
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
                isLabel = false;
                expression.clear();
                state = 0;
            } else if (state == 0 && Token.EOL == token) {
                break;
            } else {
                throw new IllegalStateException("Fatal error cannot continue!!");
            }
        }
        execute();
    }

    private void execute() {
        int pc = 0;
        LabelEntry entry=null;
        while(pc!=labelEntries.size()){
            entry = labelEntries.get(pc++);
            if (entry.variable != null) {
                variableMap.put(entry.variable, PostfixEvaluatorL4.evaluate(variableMap, entry.expression).clone());
                if(!entry.isLabel){
                    System.out.println(variableMap.get(entry.variable));
                }
            } else {
                int updatePc = evaluateGoto(entry.expression);
                pc = updatePc!= -1 ? updatePc : pc;
            }
        }
        if(entry !=null){
            if( entry.variable != null){
                variableMap.get(entry.variable).printList();
            }
            else
            {
                variableMap.get(entry.expression.peek()).printList();
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

    private int evaluateGoto(Queue<String> expression) {
        int label = getBranchValue(expression, variableMap.get(expression.peek()));
        if (label != -1) {
            return label;
        }
        return -1;
    }

    private void addLabelEntry(String var, Queue<String> expression, int label, boolean isLabel) {
        labelEntries.add(new LabelEntry(new ArrayDeque<>(expression), var,isLabel));
        if(isLabel)
            labelMap.put(label,labelEntries.size()-1 );
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



    public static void main(String[] args) throws Exception {
        int base = Num.defaultBase;
        if(args.length == 1){
            base = Integer.parseInt(args[0]);
        }
        Scanner in = new Scanner(new File("./src/LP1/lp1-l4-in3.txt"));
        LP1L4 LP1L4 = new LP1L4(base);
        LP1L4.runStateMachine(in);
    }

}
