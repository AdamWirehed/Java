// Term class for Lab2

import java.util.*;

public class Term {
    private String query;
    private long weight;

    public static void main(String[] args) {
        Term test1 = new Term("Simply", 4);
        Term test2 = new Term("Amazing", 5);
        Term test3 = new Term("Amen", 2);
        Term test4 = new Term("Amazingly", 8);

        Term[] arr = {test1, test2, test3, test4};

        System.out.println("Original List:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();

        byLexOrder LexCmp = new byLexOrder();
        Arrays.sort(arr, LexCmp);

        System.out.println("List sorted by Lexicographic order:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();


        byRevWOrder RevCmp = new byRevWOrder();
        Arrays.sort(arr, RevCmp);

        System.out.println("List sorted by Reversed weight:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();

        byPreOrder PreCmp = new byPreOrder(3);
        Arrays.sort(arr, PreCmp);

        System.out.println("List sorted by Lexicographic order for the first k chars:");
        for(Term Ttmp : arr){
            System.out.println(Ttmp.toString());
        }
        System.out.println();



    }

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight){

        if(query == null){
            throw new NullPointerException();
        }
        if(weight < 0){
            throw new IllegalArgumentException();
        }

        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in lexicographic order by query.
    public static Comparator<Term> byLexicographicOrder(){
        return new byLexOrder();
    }

    public static class byLexOrder implements Comparator<Term>{
        public int compare(Term o1, Term o2){
            return o1.query.compareTo(o2.query);
        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder(){
        return new byRevWOrder();
    }

    public static class byRevWOrder implements Comparator<Term>{
        @Override
        public int compare(Term o1, Term o2){
            return (int) (o2.weight - o1.weight);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first k characters of each query.
    public static Comparator<Term> byPrefixOrder(int k){

        if(k < 0){
            throw new IllegalArgumentException();
        }

        return new byPreOrder(k);
    }

    public static class byPreOrder implements Comparator<Term>{

        private int k;

        public byPreOrder(int k){
            this.k = k;
        }

        @Override
        public int compare(Term o1, Term o2){

            String sub1 = o1.query.substring(0, k);     // Creates substring/slice of query
            String sub2 = o2.query.substring(0, k);

            return sub1.compareTo(sub2);
        }
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by whitespace, followed by the query.
    public String toString(){
        return String.format("%12d    %s", this.weight, this.query);
    }
}
