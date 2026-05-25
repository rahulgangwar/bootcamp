
/**
 * https://leetcode.com/problems/palindrome-number/
 */
public class PalindromeNumber {

    public boolean isPalindrome(int x) {
        if(x<0) return false;

        int original = x;
        int reversed = 0;

        while(x > 0){
            reversed  = reversed * 10 + x %10;
            x = x / 10 ;
        }

        return original == reversed;
    }
}
