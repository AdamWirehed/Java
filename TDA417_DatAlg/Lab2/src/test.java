/*
Simple script for testing the classes "Term", "RangeBinarySearch" and "Autocomplete"
 */

import java.util.*;

public class test {

    public static void main(String[] args) {
        Term test1 = new Term("Simply", 4);
        Term test2 = new Term("Amazing", 5);
        Term test3 = new Term("Amen", 2);
        Term test5 = new Term("Amen", 2);
        Term test6 = new Term("Amen", 2);
        Term test7 = new Term("Amen", 2);
        Term test4 = new Term("Amazingly", 8);

        Term[] arr = {test1, test2, test3, test4, test5, test6, test7};

        Term.byRevWOrder TestRevCmp = new Term.byRevWOrder();

        System.out.println(TestRevCmp.compare(test3, test4));

        System.out.println("Original List:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();

        Autocomplete Ts = new Autocomplete(arr);
        Term[] acArr = Ts.allMatches("Ama");

        for(Term Ttmp : acArr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();


        // Old tests
        /*
        Term.byLexOrder LexCmp = new Term.byLexOrder();
        Arrays.sort(arr, LexCmp);

        System.out.println("List sorted by Lexicographic order:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();
        int ixF = RangeBinarySearch.firstIndexOf(arr, new Term("Amen", 0), LexCmp);
        int ixL = RangeBinarySearch.lastIndexOf(arr, new Term("Amen", 0), LexCmp);
        System.out.println(ixF);
        System.out.println(ixL);


        Term.byRevWOrder RevCmp = new Term.byRevWOrder();
        Arrays.sort(arr, RevCmp);

        System.out.println("List sorted by Reversed weight:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();

        Term.byPreOrder PreCmp = new Term.byPreOrder(3);
        Arrays.sort(arr, PreCmp);

        System.out.println("List sorted by Lexicographic order for the first k chars:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();

         */

    }
}
