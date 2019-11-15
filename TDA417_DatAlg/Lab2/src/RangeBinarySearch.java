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

            // If a[mid] is equal to key we move as far to the left as possible
            else{
                // Special case when mid == 0
                while(comparator.compare(a[mid], key) == 0) {
                    if(mid == 0){
                        return mid;
                    }

                    else if(comparator.compare(a[mid-1], key) < 0){
                        return mid;
                    }

                    // Could possibly implement a better solution here?
                    else{
                        mid -= 1;
                    }
                }   // End while-loop a[mid] == key
            }
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

            // If a[mid] is equal to key we move as far to the left as possible
            else{
                while(comparator.compare(a[mid], key) == 0) {
                    if(mid == a.length - 1){
                        return mid;
                    }

                    if(comparator.compare(a[mid+1], key) > 0){
                        return mid;
                    }

                    else{
                        mid += 1;
                    }
                }   // End while-loop a[mid] == key
            }
        } // End while-loop lo <= hi

        return -1;  // If no element in a is equal to key
    }
}
