package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] system;
    private WeightedQuickUnionUF full;
    private WeightedQuickUnionUF percolateChecker;
    private boolean percolate;
    private int length;
    private int openCount;
    private int topSite;
    private int bottomSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        system = new boolean[N][N]; //default = false
        int allowedNum = (N * N) + 2;
        full = new WeightedQuickUnionUF(allowedNum);
        percolateChecker = new WeightedQuickUnionUF(allowedNum);
        percolate = false;
        length = N;
        openCount = 0;
        topSite = (N * N);
        bottomSite = (N * N) + 1;
    }

    public void open(int row, int col) {
        int cell = xyTo1D(row, col);
        if (row >= length || col >= length) {
            throw new IndexOutOfBoundsException();
        } else if (!isOpen(row, col)) {
            system[row][col] = true;
            openCount += 1;
        }
        if (row == 0) {
            full.union(cell, topSite);
            percolateChecker.union(cell, topSite);
        }
        if (row == length - 1) {
            percolateChecker.union(cell, bottomSite);
        }

        setRight(row, col, cell);
        setLeft(row, col, cell);
        setDown(row, col, cell);
        setUp(row, col, cell);

        boolean toTop = percolateChecker.connected(bottomSite, topSite);
        if (toTop && !percolate) {
            percolate = true;

        }
    }      // open the site (row, col) if it is not open already

    private void setRight(int row, int col, int cell) {
        boolean right = false;
        if (col != length - 1) {
            right = isOpen(row, col + 1);
        }
        if (right) {
            int neighbor = xyTo1D(row, col + 1);
            full.union(cell, neighbor);
            percolateChecker.union(cell, neighbor);
        }
    }

    private void setLeft(int row, int col, int cell) {
        boolean left = false;
        if (col != 0) {
            left = isOpen(row, col - 1);
        }
        if (left) {
            int neighbor = xyTo1D(row, col - 1);
            full.union(cell, neighbor);
            percolateChecker.union(cell, neighbor);
        }
    }

    private void setDown(int row, int col, int cell) {
        boolean down = false;
        if (row != length - 1) {
            down = isOpen(row + 1, col);
        }
        if (down) {
            int neighbor = xyTo1D(row + 1, col);
            full.union(cell, neighbor);
            percolateChecker.union(cell, neighbor);
        }
    }

    private void setUp(int row, int col, int cell) {
        boolean up = false;
        if (row != 0) {
            up = isOpen(row - 1, col);
        }
        if (up) {
            int neighbor = xyTo1D(row - 1, col);
            full.union(cell, neighbor);
            percolateChecker.union(cell, neighbor);
        }
    }

    public boolean isOpen(int row, int col) {
        return system[row][col];
    } // is the site (row, col) open?

    public boolean isFull(int row, int col) {
        int cell = xyTo1D(row, col);
        boolean toTop = isOpen(row, col) && full.connected(cell, topSite);
        return toTop;
    } // is the site (row, col) full?

    public int numberOfOpenSites() {
        return openCount;
    }  // number of open sites

    public boolean percolates() {
        return percolate;
    }            // does the system percolate?

    private int xyTo1D(int r, int c) {
        return (r * length) + c;
    }

    public static void main(String[] args) {

    } // unit testing (not required)
}
