import java.util.*;

public class postFix{


    public static void main(String[] args) {

    String test = "((1 2 +) 5 *)";
    
    Stack tmpS = new Stack();

    for(int i = 0; i < test.length(); i++){
        char sym = test.charAt(i);
        if((sym != ' ') && (sym != '(') && (sym != ')')){
            tmpS.add(sym);
        }
    }

    System.out.println(tmpS.toString());


    return; 
    }
}