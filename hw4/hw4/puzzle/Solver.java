package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;

public class Solver {

    private MinPQ<SearchNode> queue;
    private ArrayList<Board> solution;
    private int minMoves;

    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private int moves;
        private SearchNode prev;
        private int priority;

        SearchNode(Board b, SearchNode p, int m) {
            board = b;
            prev = p;
            moves = m;
            priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode other) {
            return priority - other.priority;
        }
    }

    /** Constructor which solves the puzzle, computing
        everything necessary for moves() and solution() to
        not have to solve the problem again. Solves the
        puzzle using the A* algorithm. Assumes a solution exists.**/
    public Solver(Board initial) {
        queue = new MinPQ<SearchNode>();

        SearchNode current = new SearchNode(initial, null, 0);
        queue.insert(current);

        //SOLVE BOARD
        while (!current.board.isGoal()) {
            current = queue.delMin();
            for (Board neighbor : BoardUtils.neighbors(current.board)) {
                if (current.prev == null || !neighbor.equals(current.prev.board)) {
                    queue.insert(new SearchNode(neighbor, current,
                        current.moves + 1));
                }
            }
        }

        minMoves = current.moves;
        solution = new ArrayList<Board>();
        while (current != null) {
            solution.add(0, current.board);
            current = current.prev;
        }
    }
    /** Returns the minimum number of moves to solve the
        initial board **/
    public int moves() {
        return minMoves;
    }
    /** Returns the sequence of Boards from the initial board
        to the solution.**/
    public Iterable<Board> solution() {
        return solution;
    }


    // DO NOT MODIFY MAIN METHOD
    // Uncomment this method once your Solver and Board classes are ready.
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
        }
    }

}
