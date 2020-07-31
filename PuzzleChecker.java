import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;

/******************************************************************************
 *  Compilation:  javac-algs4 PuzzleChecker.java
 *  Execution:    java-algs4 PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java-algs4 PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

public class PuzzleChecker {
    private static final int DELAY = 1000;

    public static void draw(Board board, int n, boolean solvable, int moves) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05 * n, 1.05 * n);
        StdDraw.setYscale(-0.05 * n, 1.05 * n);
        StdDraw.filledSquare(n / 2.0, n / 2.0, n / 2.0);
        if (solvable) {
            int[][] tiles = board.gettile();
            String move = Integer.toString(moves);
            for (int row = 0; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    int number = tiles[row][col];
                    String tile = Integer.toString(number);
                    tile += ".png";
                    StdDraw.picture(col + 0.5, n - row - 0.5, tile, 1 - .1, 1 - .1);
                    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
                    StdDraw.text((n / 2.0), -0.1, "Number of Moves" + move);
                }
            }
        }
        else {
            StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 24));
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(n / 2, n / 2, "Unsolvable");
        }
    }

    private static void simulateFromFile(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdDraw.enableDoubleBuffering();
        if (solver.isSolvable()) {
            int move = 0;
            draw(initial, n, true, move);
            StdDraw.show();
            StdDraw.pause(2 * DELAY);
            for (Board x : solver.solution()) {
                draw(x, n, true, move++);
                StdDraw.show();
                StdDraw.pause(DELAY);
            }
        }
        else {
            draw(initial, n, false, -1);
            StdDraw.show();
        }

    }

    public static void main(String[] args) {

        // for each command-line argument
        String filename = args[0];
        simulateFromFile(filename);
    }
}
