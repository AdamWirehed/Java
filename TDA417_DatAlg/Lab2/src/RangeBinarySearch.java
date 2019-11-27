import java.util.*;

public class RangeBinarySearch {

    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N), where N is the length of the array
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator){

        if((a == null) || (key == null) || (comparator == null)){
            throw new NullPointerException();
        }
        int lo = 0;
        int hi = a.length - 1;

        while(lo <= hi){

            int mid = (hi + lo)/2;

            // If key is greater than a[mid] we move lo ->
            if(comparator.compare(a[mid], key) < 0){
                lo = mid + 1;
            }

            // If key is lesser than a[mid] we move hi <-
            else if(comparator.compare(a[mid], key) > 0){
                hi = mid - 1;
            }

            // If a[mid] is equal to key we check if left element of mid is lower than a[mid]
            else{
                // Special case when mid == 0
                    if(mid == 0){
                        return mid;
                    }

                    else if(comparator.compare(a[mid - 1], key) < 0){
                        return mid;
                    }

                    // Return to binary search with new hi -> yields new mid
                    else{
                        hi = mid - 1;
                    }
                }   // End while-loop a[mid] == key
        } // End while-loop lo <= hi
        return -1;  // If no element in a is equal to key
    }

    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    // Complexity: O(log N)
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator){

        if((a == null) || (key == null) || (comparator == null)){
            throw new NullPointerException();
        }

        int lo = 0;
        int hi = a.length - 1;

        while(lo <= hi){

            int mid = (hi + lo)/2;

            // If key is greater than a[mid] we move lo ->
            if(comparator.compare(a[mid], key) < 0){
                lo = mid + 1;
            }

            // If key is lesser than a[mid] we move hi <-
            else if(comparator.compare(a[mid], key) > 0){
                hi = mid - 1;
            }

            // If a[mid] is equal to key we check if left element of mid is lower than a[mid]
            else{
                // Special case when mid is at the end of the matrix
                if(mid == a.length - 1){
                    return mid;
                }

                else if(comparator.compare(a[mid + 1], key) > 0){
                    return mid;
                }

                // Return to binary search with new lo -> yields new mid
                else{
                    lo = mid + 1;
                }
            }   // End while-loop a[mid] == key
        } // End while-loop lo <= hi

        return -1;  // If no element in a is equal to key
    }
}
