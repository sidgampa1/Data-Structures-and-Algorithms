/******************************************************************************
 *  Compilation:  javac PrintEnergy.java
 *  Execution:    java PrintEnergy input.png
 *  Dependencies: SeamCarver.java
 *
 *
 *  Read image from file specified as command line argument.
 *  Print energy of each pixel as calculated by SeamCarver object.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;


public class PrintEnergy {

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        StdOut.printf("%d-by-%d image\n", picture.width(), picture.height());

        SeamCarver sc = new SeamCarver(picture);

        // System.out.println("width of pixEnergies: " + sc.pixEnergiesWidth());
        // System.out.println("height of pixEnergies: " + sc.pixEnergiesHeight());
        //
        // sc.transpose();
        //
        // System.out.println("width of transposed pixEnergies: " + sc.pixEnergiesWidth());
        // System.out.println("height of transposed pixEnergies: " + sc.pixEnergiesHeight());

        StdOut.printf("Printing energy calculated for each pixel.\n");

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++) {
                StdOut.printf("%9.0f ", sc.energy(col, row));
            }
            StdOut.println();
        }
    }

}
