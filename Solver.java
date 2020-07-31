/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private ArrayList<Board> solutionPath = new ArrayList<>();
    private MinPQ<Node> pq = new MinPQ<Node>();
    private Board current;

    public Solver(Board initial) {
        try {
            this.current = initial;
            Node node = new Node(initial, 0);
            node.parent = null;
            AstarAlgo(node);
        }
        catch (IllegalArgumentException e) {
            StdOut.print("IllegalArgument");
        }

    }

    private void AstarAlgo(Node initial) {
        ArrayList<Board> explored = new ArrayList<>();
        pq.insert(initial);
        boolean found = initial.getBoard().isGoal();
        if (found) solutionPath.add(initial.getBoard());
        while ((!pq.isEmpty()) && (!found)) {
            Node current = pq.delMin();
            Board currentBoard = current.getBoard();
            explored.add(currentBoard);
            if (currentBoard.isGoal()) found = true;
            if (found) {
                for (Node x = current; x != null; x = x.parent) {
                    solutionPath.add(x.getBoard());
                }
                Collections.reverse(solutionPath);
            }
            for (Board child : currentBoard.neighbors()) {
                if (explored.contains(child)) continue;
                Node childnode = new Node(child, current.g_score + 1);
                childnode.parent = current;
                pq.insert(childnode);
            }
        }
    }

    private class Node implements Comparable<Node> {
        public final Board current;
        public Node parent;
        public int g_score;
        public final int h_score;
        public int f_score = 0;

        public Node(Board current, int move) {
            this.current = current;
            this.g_score = move;
            this.h_score = current.hamming();
            this.f_score = g_score + h_score;
        }

        public Board getBoard() {
            return this.current;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.f_score, that.f_score);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !solutionPath.isEmpty();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        else {
            return solutionPath.size() - 1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionPath;
    }

    public static void main(String[] args) {

    }
}
