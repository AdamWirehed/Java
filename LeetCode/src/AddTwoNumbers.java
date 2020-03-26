import java.util.ArrayList;
import java.util.List;

public class AddTwoNumbers {

    public static class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2){
        int rest = 0;   // placeholder for rest variable, when a sum is 10 or higher
        int rem;
        ListNode output = new ListNode(0);
        ListNode tail = output;
        ListNode eNode = new ListNode(0); // Defining empty node

        while (l1 != eNode || l2 != eNode || rest != 0){
            int sum = l1.val + l2.val + rest;
            rest = sum / 10;
            rem = sum % 10;
            tail.next = new ListNode(rem);
            tail = tail.next;

            l1 = null != l1.next ? l1.next : eNode;
            l2 = null != l2.next ? l2.next : eNode;
        }

        return output.next;
    }

    public static void main(String[] args) {

        int[] nr1 = {2, 4, 3};
        int[] nr2 = {5, 6, 4};
        ListNode first = new ListNode(0);
        ListNode second = new ListNode(0);
        ListNode fTail = first;
        ListNode sTail = second;

        for(int i = 0; i < nr1.length; i++){
            fTail.next = new ListNode(nr1[i]);
            sTail.next = new ListNode(nr2[i]);
            fTail = fTail.next;
            sTail = sTail.next;
        }
        fTail.next = null;
        sTail.next = null;

       ListNode sum = addTwoNumbers(first.next, second.next);
       while(sum != null){
           System.out.println(sum.val);
           sum = sum.next;
       }
    }
}

