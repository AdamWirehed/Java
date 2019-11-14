/*
Simple script for testing the classes "Term", "RangeBinarySearch" and "Autocomplete"
 */

import java.util.Arrays;

public class test {

    public static void main(String[] args) {
        Term test1 = new Term("Simply", 4);
        Term test2 = new Term("Amazing", 5);
        Term test3 = new Term("Amen", 2);
        Term test4 = new Term("Amazingly", 8);

        Term[] arr = {test1, test2, test3, test4};

        Term.byRevWOrder TestRevCmp = new Term.byRevWOrder();

        System.out.println(TestRevCmp.compare(test3, test4));

        System.out.println("Original List:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();

        Term.byLexOrder LexCmp = new Term.byLexOrder();
        Arrays.sort(arr, LexCmp);

        System.out.println("List sorted by Lexicographic order:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();


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



    }
}
