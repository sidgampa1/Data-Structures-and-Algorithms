package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private Percolation tester;
    private int trials;
    private int size;

    private double[] percThresholds;

    private double mean;
    private double sd;
    private double confLow;
    private double confHigh;


    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        trials = T;
        size = N;
        percThresholds = new double[trials];

        runTests();

        mean = StdStats.mean(percThresholds);
        sd = StdStats.stddev(percThresholds);
        confLow = mean - ((1.96 * sd) / Math.sqrt(trials));
        confHigh = mean + ((1.96 * sd) / Math.sqrt(trials));


    }  // perform T independent experiments on an N-by-N grid

    private void runTests() {
        for (int i = 0; i < trials; i += 1) {
            tester = new Percolation(size);

            while (!tester.percolates()) {
                tester.open(StdRandom.uniform(size), StdRandom.uniform(size));
            }

            double numOpen = tester.numberOfOpenSites();
            double threshold = numOpen / (size * size);
            System.out.println(threshold);
            percThresholds[i] = threshold;

        }
    }
    public double mean() {
        return mean;
    }                   // sample mean of percolation threshold
    public double stddev() {
        return sd;

    }                // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return confLow;

    }           // low  endpoint of 95% confidence interval
    public double confidenceHigh() {
        return confHigh;

    }       // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(20, 1000);
        System.out.println("mean = " + stats.mean());
        System.out.println("standard dev = " + stats.stddev());
    }

}
