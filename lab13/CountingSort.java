/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author 	Akhil Batra
 * @version	1.1 - April 16, 2016
 *
**/
public class CountingSort {

    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     *  does not touch original array (non-destructive method)
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
    **/
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i] += 1;
        }

        // put the value count times into a new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     *  does not touch original array (non-destructive method)
<<<<<<< HEAD
<<<<<<< HEAD
     *
     * @param arr int array that will be sorted
=======
     * 
     * @param toSort int array that will be sorted
>>>>>>> 07f46bdd25a868e4208f0134eef3fac371240c52
=======
     * 
     * @param toSort int array that will be sorted
>>>>>>> 702ccb88ca6670630db99f5c56440777f520ed15
    **/
    public static int[] betterCountingSort(int[] toSort) {
        //TODO make it work with arrays containing negative numbers.

        //find max and min
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num: toSort) {
            if (num > max) {
                max = num;
            }
            if (num < min) {
                min = num;
            }
        }

        //fill in value counts
        int dif = max - min;
        int[] counts = new int[dif + 1];
        int shiftVal = Math.abs(min);

        for (int num: toSort) {
            counts[num + shiftVal] += 1;
        }

        // put the value count times into a new array
        int[] sorted = new int[toSort.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        return sorted;
    }
}
