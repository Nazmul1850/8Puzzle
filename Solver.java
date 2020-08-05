/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public final class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private final ArrayList<Board> solutionPath = new ArrayList<>();

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        else {
            Node node = new Node(initial, 0);
            node.parent = null;
            astarAlgo(node);

        }
    }

    private void astarAlgo(Node initial) {
        MinPQ<Node> pq = new MinPQ<Node>();
        pq.insert(initial);
        boolean found = initial.getBoard().isGoal();
        if (found) {
            solutionPath.add(initial.getBoard());
        }
        while ((!pq.isEmpty()) && (!found)) {
            Node current = pq.delMin();
            Board currentBoard = current.getBoard();
            if (currentBoard.isGoal()) found = true;
            if (found) {
                for (Node x = current; x != null; x = x.parent) {
                    solutionPath.add(x.getBoard());
                }
                Collections.reverse(solutionPath);
            }
            for (Board child : currentBoard.neighbors()) {
                if (current.parent == null || (!current.parent.getBoard().equals(child))) {
                    Node childnode = new Node(child, current.gScore + 1);
                    childnode.parent = current;
                    pq.insert(childnode);
                }
            }
        }
    }

    private class Node implements Comparable<Node> {
        private final Board current;
        private Node parent;
        private final int gScore;
        private final int fScore;

        public Node(Board current, int move) {
            this.current = current;
            this.gScore = move;
            int hScore = current.manhattan();
            this.fScore = gScore + hScore;
        }

        public Board getBoard() {
            return this.current;
        }

        public int compareTo(Node that) {
            return Integer.compare(this.fScore, that.fScore);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return !solutionPath.isEmpty();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return solutionPath.size() - 1;
        else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solutionPath.isEmpty()) return null;
        else return solutionPath;
    }

    public static void main(String[] args) {

    }
}
