import java.util.*;

public class Autocomplete {
    // Initializes the data structure from the given array of terms.
    // Complexity: O(N log N), where N is the number of terms

    private Term[] terms;

    public Autocomplete(Term[] terms){

        // Check for "null"-elements in the array
        boolean cont = Arrays.asList(terms).contains(null);
        if(cont){
            throw new IllegalArgumentException();
        }

        this.terms = terms;
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    // Complexity: O(log N + M log M), where M is the number of matching terms
    public Term[] allMatches(String prefix){

        if(prefix == null){
            throw new IllegalArgumentException();
        }

        // Sorting in lexicographic order by the lenStr character
        int lenStr = prefix.length();
        Term.byPreOrder PreCmp = new Term.byPreOrder(lenStr);
        Arrays.sort(this.terms, PreCmp);

        int ixF = RangeBinarySearch.firstIndexOf(this.terms, new Term(prefix, 0), PreCmp);
        int ixL = RangeBinarySearch.lastIndexOf(this.terms, new Term(prefix, 0), PreCmp);

        // Create return array
        if((ixF != -1) || (ixL != -1)){
            Term[] acArr = Arrays.copyOfRange(this.terms, ixF, ixL + 1);

            // Sorting the new array by reversed weight
            Term.byRevWOrder RevCmp = new Term.byRevWOrder();
            Arrays.sort(acArr, RevCmp);

            return acArr;
        }

        else{
            Term[] acArr = new Term[1];
            acArr[0] = new Term("No such element in database", 0);
            return acArr;
        }
    }

    // Returns the number of terms that start with the given prefix.
    // Complexity: O(log N)
    public int numberOfMatches(String prefix){

        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        int lenStr = prefix.length();
        System.out.println(lenStr);
        Term.byPreOrder PreCmp = new Term.byPreOrder(lenStr);
        Arrays.sort(this.terms, PreCmp);

        int ixF = RangeBinarySearch.firstIndexOf(this.terms, new Term(prefix, 0), PreCmp);
        int ixL = RangeBinarySearch.lastIndexOf(this.terms, new Term(prefix, 0), PreCmp);

        return (ixL - ixF) + 1;
    }
}