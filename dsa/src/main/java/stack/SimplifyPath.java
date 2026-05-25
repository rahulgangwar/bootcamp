package stack;

import java.util.Stack;

/*
* https://leetcode.com/problems/simplify-path/?envType=study-plan-v2&envId=top-interview-150
*/
public class SimplifyPath {
    public String simplifyPath(String path) {
        String[] data = path.split("/");
        Stack<String> s = new Stack<>();

        for (String curr : data) {
            if (curr.equals("..") && !s.empty()) s.pop();
            else if (!curr.equals(".") && !curr.isEmpty() && !curr.equals("..")) {
                s.push(curr);
            }
        }

        return "/" + String.join("/", s);
    }
}
