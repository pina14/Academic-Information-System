package pt.isel.ls.Phase0;

public class Ints {
    public static int max(int a, int b){
        return a >= b ? a : b;
    }

    public static int indexOfBinary(int[] a, int fromIndex, int toIndex, int n) {
        /* Added the conditions to make sure the arguments passed in parameters are correct */
        if (fromIndex > toIndex || fromIndex < 0 || toIndex > a.length )
            throw new IllegalArgumentException();

        int mid;
        int low = fromIndex;
        int high = toIndex - 1;
        /* Changed the condition from "<" to "<=" because case low equals high still exists the possibility to the n value be there */
        while(low <= high){
            /* Changed the mid calculation from "mid = high + low / 2 + 1" to the current mid calculation to get the correct result */
            mid = (high + low) / 2;
            if(n > a[mid]) low = mid + 1;
            else if(n < a[mid]) high = mid - 1;
            else return mid;
        }
        return -1;
    }
}
