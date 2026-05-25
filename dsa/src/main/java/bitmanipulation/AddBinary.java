package bitmanipulation;

// https://leetcode.com/problems/add-binary

public class AddBinary {
    public String addBinary(String a, String b) {
        int i = a.length()-1;
        int j = b.length()-1;

        StringBuilder sb = new StringBuilder();
        int carry = 0;
        while(i>=0 || j>=0 || carry ==1){
            int x=0;
            int y=0;
            if(i>=0){
                x = Character.getNumericValue(a.charAt(i));
            }
            if(j>=0){
                y = Character.getNumericValue(b.charAt(j));
            }
            int sum  = x+y+carry;
            if(sum >= 2){
                sb.append(sum%2);
                carry = 1;
            }else{
                sb.append(sum);
                carry = 0;
            }
            i--;
            j--;
        }
        return sb.reverse().toString();
    }
}
