/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    private int n;

    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        else {
            this.n = tiles[0].length;
            this.tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    this.tiles[i][j] = tiles[i][j];
                }
            }
        }

    }

    public int[][] gettile() {
        return tiles;
    }

    // string representation of this board
    public String toString() {
        StringBuilder board = new StringBuilder();
        board.append(Integer.toString(this.n));
        board.append('\n');
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int temp = tiles[i][j];
                board.append(Integer.toString(temp));
                board.append(" ");
            }
            board.append('\n');
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamdist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != getGoaltile()[i][j] && tiles[i][j] != 0) hamdist++;
            }
        }
        return hamdist;
    }

    private int[] getindex(int x, int[][] tile) {
        int[] point = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tile[i][j] == x) {
                    point[0] = i;
                    point[1] = j;
                    break;
                }
            }
        }
        return point;
    }

    private int[][] getGoaltile() {
        int count = 1;
        int[][] goaltile = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goaltile[i][j] = count++;
            }
        }
        goaltile[n - 1][n - 1] = 0;
        return goaltile;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != getGoaltile()[i][j] && tiles[i][j] != 0) {
                    int[] point = getindex(tiles[i][j], getGoaltile());
                    sum += Math.abs(i - point[0]) + Math.abs(j - point[1]);
                }
            }
        }
        return sum;

    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        else if (getClass() != y.getClass()) return false;
        else {
            Board that = (Board) y;
            if (n != that.n) {
                return false;
            }
            else {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (this.tiles[i][j] != that.tiles[i][j]) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }

    // all neighboring boards

    public Iterable<Board> neighbors() {
        return getNeighbours();
    }

    private int[][] swap(int x1, int y1, int x2, int y2, int[][] nTiles) {
        int temp = nTiles[x2][y2];
        nTiles[x2][y2] = nTiles[x1][y1];
        nTiles[x1][y1] = temp;
        return nTiles;
    }

    private int[][] copytile(int[][] copyfrom) {
        int newN = copyfrom[0].length;
        int[][] copyto = new int[newN][newN];
        for (int i = 0; i < newN; i++) {
            for (int j = 0; j < newN; j++) {
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
        if (x + 1 < n) {
            int[][] newtiles = swap(x, y, x + 1, y, copytile(tiles));
            itr.add(new Board(newtiles));
        }
        if (x - 1 >= 0) {
            int[][] newtiles = swap(x, y, x - 1, y, copytile(tiles));
            itr.add(new Board(newtiles));
        }

        if (y - 1 >= 0) {
            int[][] newtiles = swap(x, y, x, y - 1, copytile(tiles));
            itr.add(new Board(newtiles));
        }
        if (y + 1 < n) {
            int[][] newtiles = swap(x, y, x, y + 1, copytile(tiles));
            itr.add(new Board(newtiles));
        }
        return itr;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twintiles;
        int x = n / 2;
        int y1 = 0;
        int y2 = 1;
        int[] point = getindex(0, tiles);
        if (n == 2) {
            if (point[0] == 0) x = 1;
            else x = 0;
        }
        else {
            if (x == point[0] && y1 == point[1]) {
                y1++;
                y2++;
            }
            if (x == point[0] && y2 == point[1]) {
                y2++;
            }

        }
        twintiles = swap(x, y1, x, y2, copytile(tiles));
        Board board = new Board(twintiles);
        return board;
    }

    public static void main(String[] args) {

    }
}
