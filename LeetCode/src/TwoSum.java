import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {

    public static void main(String[] args) {

        int[] numbers = {-18, 12, 3, 0};
        int[] indices = twoSum(numbers, -6);
        System.out.println(Arrays.toString(indices));

    }

    public static int[] twoSum(int[] nums, int target){
        HashMap<Integer, Integer> visited = new HashMap<>();
        int dif;
        int[] index = new int[2];

        for(int ix = 0; ix < nums.length; ix++){
            dif = target - nums[ix];

            if(!visited.containsKey(dif)){
                visited.put(nums[ix], ix);
            }

            else{
                index[0] = visited.get(dif);
                index[1] = ix;
                break;
            }
        }
        return index;
    }
}
