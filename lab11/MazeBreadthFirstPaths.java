import java.util.Observable;
import edu.princeton.cs.algs4.Queue;
/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int source;
    private int target;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;    
    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int x) {
        Queue<Integer> q = new Queue<Integer>();
        distTo[x] = 0;
        marked[x] = true;
        announce();
        q.enqueue(x);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            if (v == target) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    announce();
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(source);
    }
}
