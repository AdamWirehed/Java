import java.util.*;

public class methods {
    public static void main(String[] args){
        Character[] cRay = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd', '!'};
        Integer[] iRay = {1, 2, 3, 4, 5};

        System.out.println(Arrays.toString(cRay));
        reverse(cRay);
        System.out.println(Arrays.toString(cRay));

        System.out.println(Arrays.toString(iRay));
        reverse(iRay);
        System.out.println(Arrays.toString(iRay));

    }

    // Generic "<T>" does not work on primitives (int, char etc.) and only on Objects (Integer, Character)
    private static <T> void reverse(T[] arr){
        int front = 0;
        int back = arr.length - 1;
        while (front < back) {
            swap(arr, front, back);
            ++front;
            --back;
        }
    }

    private static <T> void swap(T[] arr, int front, int back){
            T temp = arr[front];
            arr[front] = arr[back];
            arr[back] = temp;
    }
}
