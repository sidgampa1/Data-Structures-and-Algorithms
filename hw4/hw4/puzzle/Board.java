package hw4.puzzle;

public class Board {

    private int[][] board;
    private int length;
    private int hamming;
    private int manhattan;
    /** Constructs a board from an N-by-N array of tiles where
      * tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {
        length = tiles.length;
        board = new int[length][length];
        hamming = 0; //to be evaluated in loop
        manhattan = 0; //to be evaluated in loop
        for (int row = 0; row < length; row += 1) {
            for (int col = 0; col < length; col += 1) {
                int tile = tiles[row][col];
                int perfectTile;
                if (row == length - 1 && col == length - 1) {
                    perfectTile = 0;
                } else {
                    perfectTile = (row * length) + col + 1;
                }
                board[row][col] = tile; //create board matrix

                //goal board check, hamming and manhattan calc.
                int goalCol; // for mini manhattan calc
                int goalRow; // for mini manhattan calc
                if (tile != perfectTile) {
                    hamming += 1;

                    if (tile == 0) {
                        goalRow = length - 1;
                        goalCol = length - 1;
                    } else {
                        goalRow = (tile / length);
                        goalCol = (tile % length) - 1;
                    }

                    manhattan += Math.abs(goalRow - row) + Math.abs(goalCol - col);

                }


            }
        }
    }
    /** Returns value of tile at row i, column j (or 0 if blank) **/
    public int tileAt(int i, int j) {
        if (i < 0 || j < 0 || i >= length || j >= length) {
            throw new IndexOutOfBoundsException("i = " + i + "j = " + j);
        }
        return board[i][j];
    }
    /** Returns the board size N **/
    public int size() {
        return length;
    }
    /** Hamming priority function**/
    public int hamming() {
        return hamming;
    }
    /** Manhattan priority function**/
    public int manhattan() {
        return manhattan;
    }
    /** Returns true if is this board the goal board **/
    public boolean isGoal() {
        return (hamming == 0);
    }

    // private int 2DtoTile(int row, int col) {
    //     if (row == length - 1 && col == length - 1) {
    //         return 0;
    //     }
    //     else {
    //         return (row * length) + col + 1;
    //     }
    // }

    /** Returns true if this board's tile values are the same
      * position as y's **/
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (this == null) {
            return false;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;
        if (size() != that.size()) {
            return false;
        }

        for (int row = 0; row < length; row += 1) {
            for (int col = 0; col < length; col += 1) {
                if (tileAt(row, col) != that.tileAt(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        return 1;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
