/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    private int[][] goaltile;
    private int N;

    public Board(int[][] tiles) {
        this.tiles = tiles.clone();
        this.N = tiles[0].length;
        int count = 1;
        goaltile = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goaltile[i][j] = count++;
            }
        }
        goaltile[N - 1][N - 1] = 0;

    }

    public int[][] gettile() {
        return tiles;
    }

    // string representation of this board
    public String toString() {
        String board = "";
        board += Integer.toString(this.N);
        board += '\n';
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int temp = tiles[i][j];
                board += Integer.toString(temp);
                board += " ";
            }
            board += '\n';
        }
        return board;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int hamdist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != goaltile[i][j] && tiles[i][j] != 0) hamdist++;
            }
        }
        return hamdist;
    }

    private int[] getindex(int x, int[][] tile) {
        int[] point = new int[2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tile[i][j] == x) {
                    point[0] = i;
                    point[1] = j;
                    break;
                }
            }
        }
        return point;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != goaltile[i][j] && tiles[i][j] != 0) {
                    int[] point = getindex(tiles[i][j], goaltile);
                    sum += Math.abs(i - point[0]) + Math.abs(j - point[1]);
                }
            }
        }
        return sum;

    }

    public void show() {
        StdOut.println(hamming());
        StdOut.println(manhattan());
        StdOut.println(isGoal());
        StdOut.println(this);
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (hamming() == 0) return true;
        else return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board that = (Board) y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards

    public Iterable<Board> neighbors() {
        return getNeighbours();
    }

    private void swap(int x1, int y1, int x2, int y2) {
        int temp = tiles[x2][y2];
        tiles[x2][y2] = tiles[x1][y1];
        tiles[x1][y1] = temp;
    }

    private int[][] copytile(int[][] copyfrom) {
        int n = copyfrom[0].length;
        int[][] copyto = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyto[i][j] = copyfrom[i][j];
            }
        }
        return copyto;
    }


    private ArrayList<Board> getNeighbours() {
        ArrayList<Board> itr = new ArrayList<Board>();
        int[] index = getindex(0, tiles);
        int x = index[0];
        int y = index[1];
        if (x + 1 < N) {
            swap(x, y, x + 1, y);
            int[][] newtiles = copytile(tiles);
            itr.add(new Board(newtiles));
            swap(x + 1, y, x, y);
        }
        if (x - 1 >= 0) {
            swap(x, y, x - 1, y);
            int[][] newtiles = copytile(tiles);
            itr.add(new Board(newtiles));
            swap(x - 1, y, x, y);
        }

        if (y - 1 >= 0) {
            swap(x, y, x, y - 1);
            int[][] newtiles = copytile(tiles);
            itr.add(new Board(newtiles));
            swap(x, y - 1, x, y);
        }
        if (y + 1 < N) {
            swap(x, y, x, y + 1);
            int[][] newtiles = copytile(tiles);
            itr.add(new Board(newtiles));
            swap(x, y + 1, x, y);
        }
        return itr;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twintiles;
        int x1, x2, y1, y2;
        int[] point = getindex(0, tiles);
        do {
            x1 = StdRandom.uniform(N);
            y1 = StdRandom.uniform(N);
            x2 = StdRandom.uniform(N);
            y2 = StdRandom.uniform(N);
        } while ((x1 == x2 && y1 == y2) || ((x1 == point[0] && y1 == point[1]) || (x2 == point[0]
                && y2 == point[1])));
        swap(x1, y1, x2, y2);
        twintiles = copytile(tiles);
        swap(x2, y2, x1, y1);
        Board board = new Board(twintiles);
        return board;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        StdOut.print(initial.twin());
    }
}
