public class ReverseInteger {

    public static void main(String[] args) {
        int test = 432;
        int output = reverse(test);
        System.out.println(output);
    }

    public static int reverse(int x) {
        String tmpStr = Integer.toString(x);
        char[] charArr = tmpStr.toCharArray();
        char tmp;
        int hi = charArr.length - 1;
        int lo;

        if (charArr[0] == '-') {
            lo = 1;
        } else {
            lo = 0;
        }

        while (lo < hi) {
            tmp = charArr[lo];
            charArr[lo] = charArr[hi];
            charArr[hi] = tmp;
            lo += 1;
            hi -= 1;
        }

        tmpStr = new String(charArr);
        double rev = Double.parseDouble(tmpStr);
        if (Math.abs(rev) > 2147483647) {
            return 0;
        } else {
            return Integer.parseInt(tmpStr);
        }
    }
}
