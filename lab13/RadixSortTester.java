public class RadixSortTester {

    public static void main(String[] args) {
        String[] words = new String[5];
        words[0] = "hello";
        words[1] = "this";
        words[2] = "is";
        words[3] = "a";
        words[4] = "test";

        String[] sorted = RadixSortTester.sort(words);

        System.out.println(sorted);
    }

}
