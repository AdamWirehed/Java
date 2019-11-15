// Term class for Lab2

import java.util.*;

public class Term {
    private String query;
    private long weight;

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
            return o1.query.compareTo(o2.query);    // Returns 0 if equal, o1 > o2 => positive num. o2 > o1 => neg. num.
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
        public int compare(Term o1, Term o2) {

            // Scaling slice index to the words
            int s1 = Math.min(k, o1.query.length());
            int s2 = Math.min(k, o2.query.length());

            String sub1 = o1.query.substring(0, s1);     // Creates substring/slice of query
            String sub2 = o2.query.substring(0, s2);

            return sub1.compareTo(sub2);
        }
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by whitespace, followed by the query.
    public String toString(){
        return String.format("%12d    %s", this.weight, this.query);
    }
}
