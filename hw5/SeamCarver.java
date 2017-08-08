import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private double[][] pixEnergies;
    private double[][] transposeEnergies;
    private double[][] minPathVals;

    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        pixEnergies = new double[height()][width()];
        transposeEnergies = new double[width()][height()];
        for (int row = 0; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                double energy = calcEnergy(row, col);
                pixEnergies[row][col] = energy;
                transposeEnergies[col][row] = energy;
            }
        }
    }

    private double calcEnergy(int row, int col) {

        Color left = getColor(col - 1, row);
        Color right = getColor(col + 1, row);
        Color up = getColor(col, row - 1);
        Color down = getColor(col, row + 1);

        double xGrad = calcGrad(left, right);
        double yGrad = calcGrad(up, down);

        return xGrad + yGrad;
    }

    private double calcGrad(Color left, Color right) {
        double red = Math.abs(right.getRed() - left.getRed());
        double blue = Math.abs(right.getBlue() - left.getBlue());
        double green = Math.abs(right.getGreen() - left.getGreen());

        return Math.pow(red, 2) + Math.pow(blue, 2) + Math.pow(green, 2);
    }

    private Color getColor(int col, int row) {
        if (col == width()) {
            col = 0;
        } else if (col < 0) {
            col = width() - 1;
        }
        if (row == height()) {
            row = 0;
        } else if (row < 0) {
            row = height() - 1;
        }

        return picture.get(col, row);
    }

    public Picture picture() {
        return new Picture(picture);
    }    // current picture

    public int width() {
        return picture.width();
    }   // width of current picture

    public int height() {
        return picture.height();
    }   // height of current picture

    public double energy(int x, int y) {
        if (x >= pixEnergiesWidth() || x < 0
            || y >= pixEnergiesHeight() || y < 0) {
            throw new IllegalArgumentException();
        }
        return pixEnergies[y][x];
    }   // energy of pixel at column x and row y

    private int pixEnergiesWidth() {
        return pixEnergies[0].length;
    }

    private int pixEnergiesHeight() {
        return pixEnergies.length;
    }

    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    } // sequence of indices for horizontal seam

    private void transpose() {
        double[][] temp = pixEnergies;
        pixEnergies = transposeEnergies;
        transposeEnergies = temp;
    }

    private double getUpperPath(int row, int col) {
        return minPathVals[row - 1][col];
    }

    private double getUpperRightPath(int row, int col) {
        if (col != pixEnergiesWidth() - 1) {
            return minPathVals[row - 1][col + 1];
        }
        return Double.MAX_VALUE;
    }

    private double getUpperLeftPath(int row, int col) {
        if (col != 0) {
            return minPathVals[row - 1][col - 1];
        }
        return Double.MAX_VALUE;
    }

    public int[] findVerticalSeam() {

        //create minPaths matrix
        minPathVals = new double[pixEnergiesHeight()][pixEnergiesWidth()];
        for (int row = 0; row < minPathVals.length; row += 1) {
            for (int col = 0; col < minPathVals[0].length; col += 1) {

                if (row == 0) {
                    minPathVals[row][col] = energy(col, row);
                } else {
                    double current = energy(col, row);
                    double upperRight = getUpperRightPath(row, col);
                    double upperLeft = getUpperLeftPath(row, col);
                    double upperTop = getUpperPath(row, col);

                    double minPath = current + Math.min(Math.min(upperRight, upperLeft), upperTop);

                    minPathVals[row][col] = minPath;
                }
            }
        }

        int row = minPathVals.length - 1;
        int col = findMinCol();
        int[] seam = new int[pixEnergiesHeight()];
        seam[pixEnergiesHeight() - 1] = col;

        while (row > 0) {
            double minPath = minPathVals[row][col];
            double upperRight = getUpperRightPath(row, col);
            double upperLeft = getUpperLeftPath(row, col);
            double upperTop = getUpperPath(row, col);

            int minCol = getMinEnergyCol(col, upperLeft, upperRight, upperTop);

            row -= 1;
            seam[row] = minCol;
            col = minCol;
        }

        return seam;
    }  // sequence of indices for vertical seam


    private int findMinCol() {
        double[] list = minPathVals[minPathVals.length - 1];
        int counter = 0;
        double min = Double.MAX_VALUE;
        int col = 0;

        while (counter < list.length) {

            if (list[counter] < min) {
                min = list[counter];
                col = counter;
            }
            counter += 1;
        }

        return col;
    }

    private int getMinEnergyCol(int col, double left, double right, double upper) {

        if (upper == left) {
            return col + 1;
        } else if (left == right) {
            return col;
        } else if (upper == right) {
            return col - 1;
        }
        double min = Math.min(left, Math.min(right, upper));

        if (min == right && upper != right && left != right) {
            return col + 1;
        } else if (min == left && upper != left && right != left) {
            return col - 1;
        }
        return col;

    }


    public void removeHorizontalSeam(int[] seam) {
        picture = SeamRemover.removeHorizontalSeam(picture, seam);
        pixEnergies = new double[height()][width()];
        transposeEnergies = new double[width()][height()];
        for (int row = 0; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                double energy = calcEnergy(row, col);
                pixEnergies[row][col] = energy;
                transposeEnergies[col][row] = energy;
            }
        }

    }  // remove horizontal seam from picture

    public void removeVerticalSeam(int[] seam) {
        picture = SeamRemover.removeVerticalSeam(picture, seam);
        pixEnergies = new double[height()][width()];
        transposeEnergies = new double[width()][height()];
        for (int row = 0; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                double energy = calcEnergy(row, col);
                pixEnergies[row][col] = energy;
                transposeEnergies[col][row] = energy;
            }
        }
    }  // remove vertical seam from picture
}
