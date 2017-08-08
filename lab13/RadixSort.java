/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra
 * @version 1.4 - April 14, 2016
 *
 **/
public class RadixSort
{

    /**
     * Does Radix sort on the passed in array with the following restrictions:
     *  The array can only have ASCII Strings (sequence of 1 byte characters)
     *  The sorting is stable and non-destructive
     *  The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     **/
    public static String[] sort(String[] asciis)
    {
        String[] sorted = copyArray(asciis);
        sortHelper(sorted, 0, sorted.length, 0);
        return sorted;
    }

    private static int findMaxLength(String[] asciis) {
        int max = 0;
        for (String string: asciis) {
            if (string.length() > max) {
                max = string.length();
            }
        }
        return max;
    }

    private static String[] copyArray(String[] toCopy) {
        String[] copied = new String[toCopy.length];
        for (int i = 0; i < toCopy.length; i += 1) {
            copied[i] = new String(toCopy[i]);
        }
        return copied;
    }

    /**
     * Radix sort helper function that recursively calls itself to achieve the sorted array
     *  destructive method that changes the passed in array, asciis
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelper(String[] asciis, int start, int end, int index)
    {
        boolean isDone = true;
        // TODO use if you want to
        int[] counts = new int[256];
        // get int val of char in each string
        // place num of each val in counts[]
        for (int i = start; i < end; i += 1) {
            String word = asciis[i];
            int charVal = getCharVal(word, index);
            if (charVal != 0) {
                isDone = false;
            }
            counts[charVal] += 1;
        }

        if (isDone) {
            return;
        }

        int[] countCopy = condense(counts);

        //place Strings in new array based on counts[] indices
        String[] sorted = new String[asciis.length];
        for (int i = start; i < end; i += 1) {
            String word = asciis[i];
            int charVal = getCharVal(word, index);
            counts[charVal] -= 1;
            int place = counts[charVal];
            sorted[place] = word;
        }

        int startInd = 0;
        for (int i = 0; i < countCopy.length; i += 1) {
            int endInd = countCopy[i];
            if (endInd > start) {
                sortHelper(sorted, startInd, endInd, index + 1);
            }
            startInd = endInd;
        }

    }

    private static int getCharVal(String word, int index) {
        int count = 0;
        if (index < word.length()) {
            count = (int) word.charAt(index);
        }
        return count;
    }

    //condense counts[] to form index starting points
    private static int[] condense(int[] counts) {
        int[] countCopy = new int[counts.length];
        countCopy[0] = counts[0];
        for (int i = 1; i < counts.length; i += 1) {
            counts[i] = counts[i - 1] + counts[i];
            countCopy[i] = counts[i];
        }
        return countCopy;
    }
}
