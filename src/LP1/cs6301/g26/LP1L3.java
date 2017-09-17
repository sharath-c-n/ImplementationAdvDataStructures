package cs6301.g26;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Group: g26
public class LP1L3 {
	public enum Token { VAR, NUM, OP, EQ , EOL }

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
		} else {  // Error
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
		int state = 0;
		StringBuilder postfixExp = new StringBuilder();
		Map<Character,Num> valueMap = new HashMap<>();
		char var = 0 ;
		while(in.hasNext()) {
			String s = in.next();
			Token t = tokenize(s);
			if(state==0 && Token.VAR == t){
				var = s.charAt(0);
				state++;
			}
			else if(state ==1 && Token.EQ == t){
				state++;
			}
			else if(state ==2 && Token.NUM == t){
				state=3;
				valueMap.put(var,new Num(s));
			}
			else if(state ==2 && (Token.VAR == t || Token.OP == t)){
				postfixExp.append(s);
			}
			else if(state ==2 && (Token.EOL == t)){
				Num result = PostfixEvaluator.evaluate(valueMap,postfixExp.toString());
				valueMap.put(var,result);
				state = 0;
				System.out.println(valueMap.get(var));
			}
			else if(state == 0 && Token.EOL == t){
				valueMap.get(var).printList();
				break;
			}
			else if(state == 3 && Token.EOL == t){
				System.out.println(valueMap.get(var));
				state = 0;
			}
			else if(Token.EOL == t){
				System.out.println(valueMap.get(var));
			}
			else
			{
				throw new IllegalStateException("Fatal error cannot continue!!");
			}
		}
	}
}
