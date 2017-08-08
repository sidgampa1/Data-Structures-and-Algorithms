package hw3.hash;

import java.util.HashSet;
import java.util.Set;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        double scale = 1.0;
        int N = 50;
        int M = 10;

        HashTableDrawingUtility.setScale(scale);
        Set<Oomage> oomies = new HashSet<Oomage>();
        for (int i = 0; i < N; i += 1) {
            oomies.add(ComplexOomage.randomComplexOomage());
        }
        visualize(oomies, M, scale);
    }

    public static void visualize(Set<Oomage> set, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);

        /* TODO: Create a visualization of the given hash table. Use
           du.xCoord and du.yCoord to figure out where to draw
           Oomages.
         */
         int code;
         int bucket;
         int bucketPos;
         int[] positions = new int[M];
         double x;
         double y;
         HashTableDrawingUtility.setScale(scale);
         for (Oomage oomage: set) {
             //GET VISULAIZATION INFO FROM HASHSET
             code = oomage.hashCode(); // get hashcode
             bucket = (code & 0x7FFFFFFF) % M; // get bucket number
             bucketPos = positions[bucket]; // get position in bucket
             positions[bucket] += 1; // update position in bucket

             //VISUALIZE
             x = HashTableDrawingUtility.xCoord(bucketPos);
             y = HashTableDrawingUtility.yCoord(bucket, M);
             oomage.draw(x, y, scale);
         }
        /* When done with visualizer, be sure to try
           scale = 0.5, N = 2000, M = 100. */
    }
}
