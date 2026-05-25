package stack;

import java.util.Arrays;
import java.util.Stack;

// https://leetcode.com/problems/evaluate-reverse-polish-notation/?envType=study-plan-v2&envId=top-interview-150
public class EvalReversePolishNotation {
    public int evalRPN(String[] tokens) {
        Stack<Integer> s = new Stack<>();

        for (String token : tokens) {
            if (Arrays.asList("+", "-", "*", "/").contains(token)) {
                int y = s.pop();
                int x = s.pop();

                if (token.equals("+")) {
                    s.push(x + y);
                }
                if (token.equals("-")) {
                    s.push(x - y);
                }
                if (token.equals("/")) {
                    s.push(x / y);
                }
                if (token.equals("*")) {
                    s.push(x * y);
                }
            } else {
                s.push(Integer.parseInt(token));
            }
        }
        return s.pop();
    }
}
