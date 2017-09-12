import java.io.File;
import java.util.*;

public class L4Driver {
    public enum Token { VAR, NUM, OP, EQ , EOL ,QUES, COL }

    public static Token tokenize(String s) throws Exception {
        if(s.matches("\\d+")) {  // one or more digits
            return Token.NUM;
        } else if(s.matches("[a-z]")) {  // letter
            return Token.VAR;
        } else if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%") || s.equals("^") || s.equals("|")) {
            return Token.OP;
        } else if(s.equals("=")) {
            return Token.EQ;
        }  else if(s.equals(";")) {
            return Token.EOL;
        }
        else if(s.equals("?")) {
            return Token.QUES;
        }
        else if(s.equals(":")) {
            return Token.COL;
        }
        else {  // Error
            throw new Exception("Unknown token: " + s);
        }
    }

    /**
     * This function uses state machine to evaluate the input string
     * State 0 : Is the starting point of SM and expects a variable or a semicolon
     * State 1 : Expects an equals symbol
     * State 2 : The next token could be either a Number or a variable, if a varable is received
     *            the state will not be changed untill we receive a semicolon at which point the state
     *            is reset to 0.
     * State 3 : if we receive a number in state 3 we will go to state 3 and this expects the next token to be
     *          a semicolon.
     * @param args : not used
     * @throws Exception : if the input statement is wrong, illegal state exception is thrown.
     */

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("./src/LP1/lp1-l3-in1.txt"));
        HashMap<Integer,LabelEntry> labelEntries = new HashMap<>();
        HashMap<String,Num> variableMap = new HashMap<>();
        Queue<String> expression = new ArrayDeque<>();
        int constantsCount = 0;
        String variable="";
        boolean isLabel=false;
        int lastLabel=-1,label=-1,state = 0;
       while(in.hasNext()){
           String symbol = in.next();
           Token token = tokenize(symbol);
            if(state == 0  && Token.VAR == token) {
                state = 2;
                variable = symbol;
                isLabel = false;
                lastLabel = label = -1;
            }
            else if(state == 0  && Token.NUM == token) {
                state++;
                lastLabel = label;
                label = Integer.parseInt(symbol);
                isLabel = true;
            }
            else if(state == 1 && Token.VAR == token){
                variable = symbol;
                state++;
            }
            else if(state == 2 && Token.EQ == token){
                state++;
            }
            else if(state == 2 && Token.EOL == token){
                System.out.println(variableMap.get(variable));
                state = 0;
            }
            else if(state == 2 && Token.QUES == token && isLabel ){
                state = 6;
                expression.add(variable);
                expression.add(symbol);
            }

            else if(state == 3 && Token.NUM == token){
                variableMap.put(variable,new Num(symbol));
                if(isLabel)
                    expression.add(symbol);
                state++;
            }
            else if(state == 3 && Token.VAR == token ){
                state = 5;
                expression.add(symbol);
            }
            else if(state == 4 && Token.EOL == token){
                if(isLabel){
                    addLabelEntry(labelEntries,variable,expression,label,lastLabel);
                    isLabel = false;
                }
                System.out.println(variableMap.get(variable));
                expression.clear();
                state = 0;
            }
            else if(state == 5 && (Token.VAR == token || Token.OP == token) ){
                expression.add(symbol);
            }
            else if(state == 5 && Token.NUM == token){
                variableMap.put("t"+constantsCount,new Num(symbol));
                expression.add("t"+constantsCount++);
            }
            else if(state == 5 && Token.EOL == token ){

                if(isLabel){
                    expression = ShuntingYard.parse(expression);
                    addLabelEntry(labelEntries,variable,expression,label,lastLabel);
                    isLabel = false;
                }
                variableMap.put(variable,PostfixEvaluatorV2.evaluate(variableMap,expression));
                System.out.println(variableMap.get(variable));
                expression.clear();
                state=0;
            }
            else if((state == 6 || state == 8 )&& Token.NUM == token){
                state++;
                expression.add(symbol);
            }
            else if(state == 7 && Token.COL == token){
                state++;
                expression.add(symbol);
            }
            else if((state == 7|| state == 9) && Token.EOL == token){
                addLabelEntry(labelEntries,null,expression,label,lastLabel);
                executeGoto(labelEntries,expression,variableMap);
                isLabel = false;
                expression.clear();
                state = 0;
            }
            else if(state ==0 && Token.EOL == token){
                variableMap.get(variable).printList();
                break;
            }
            else
            {
                throw new IllegalStateException("Fatal error cannot continue!!");
            }
       }
    }

    private static void executeGoto(HashMap<Integer, LabelEntry> lableEntries, Queue<String> expression, HashMap<String, Num> variableMap) {
        int label = getBranchValue(expression,variableMap.get(expression.peek()));
        if(label != -1){
            executeFromTable(label,lableEntries,variableMap);
        }
        return;
    }

    private static void addLabelEntry(HashMap<Integer, LabelEntry> labelEntries, String var, Queue<String> expression, int label, int lastLabel) {
        labelEntries.put(label,new LabelEntry(new ArrayDeque<>(expression),var));
        if(lastLabel != -1){
            labelEntries.get(lastLabel).setNextLine(label);
        }
    }

    private static void executeFromTable(int branchTo, Map<Integer, LabelEntry> labelTable, HashMap<String, Num> variableMap) {
            while (branchTo!= -1){
                LabelEntry entry = labelTable.get(branchTo);
                if(entry.getVariable()!=null){
                    variableMap.put(entry.getVariable(),PostfixEvaluatorV2.evaluate(variableMap,entry.getExpression()));
                    branchTo = entry.nextLine;
                }
                else {
                    branchTo = getBranchValue(entry.getExpression(),variableMap.get(entry.getExpression().peek()));
                }
            }
    }

    private static int getBranchValue(Queue<String> infixExp,Num num) {
        Iterator<String> iterator = infixExp.iterator();
        String branchTo = "";
        if(num.isZero()){
            if(infixExp.contains(":")){
                while(iterator.hasNext()){
                    branchTo = iterator.next();
                }
                return Integer.parseInt(branchTo);
            }
        }
        else{
            while(iterator.hasNext() && !branchTo.equals("?")){
                branchTo = iterator.next();
            }
            return Integer.parseInt(iterator.next());
        }
        return -1;
    }

    private static class LabelEntry {
        Queue<String> expression;
        String variable ;
        int nextLine;


        public LabelEntry(Queue<String> expression,String variable) {
            this.expression = expression;
            this.variable = variable;
            this.nextLine = -1;
        }

        public void setNextLine(Integer nextLine) {
            this.nextLine = nextLine;
        }

        public Queue<String> getExpression() {
            return expression;
        }

        public String getVariable() {
            return variable;
        }
    }
}
