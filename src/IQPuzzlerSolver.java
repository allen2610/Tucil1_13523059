import java.util.List;

public class IQPuzzlerSolver {
    private char[][] board;
    private List<Piece> pieces;
    private long totalCase = 0;

    public IQPuzzlerSolver(char[][] board, List<Piece> pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    public boolean solvePuzzle() {
        long start = System.currentTimeMillis();
        if (bruteForce(0) && penuhBoard()) {
            System.out.println("Solusi Puzzle:");
            printBoard();
            long end = System.currentTimeMillis();
            long totalTime = end - start;

            System.out.println("Waktu pencarian: " + totalTime + " ms");
            System.out.println();
            System.out.println("Banyak kasus yang ditinjau: " + totalCase);
            System.out.println();
            return true;
        } else {
            System.out.println("Maaf, solusi tidak dapat ditemukan oleh program :(");
            return false;
        }
    }

    private boolean bruteForce(int index) {
        if (index == pieces.size()) {
            return true;
        }

        Piece piece = pieces.get(index);
        List<char[][]> transformations = piece.getPieceVersions(); 

        for (char[][] shape : transformations) {
            for (int r = 0; r <= board.length - shape.length; r++) {
                for (int c = 0; c <= board[0].length - shape[0].length; c++) {
                    totalCase++;
                    if (valid(shape, r, c)) {
                        masukinPiece(shape, r, c, piece.getName());
                        if (bruteForce(index + 1)) return true;
                        hapusPiece(shape, r, c);
                    }
                }
            }
        }

        return false;
    }

    private boolean valid(char[][] shape, int row, int col) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != ' ') {
                    if (board[row + r][col + c] != '1') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void masukinPiece(char[][] shape, int row, int col, char label) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != ' ') {
                    board[row + r][col + c] = label;
                }
            }
        }
    }

    private void hapusPiece(char[][] shape, int row, int col) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != ' ') {
                    board[row + r][col + c] = '1';
                }
            }
        }
    }

    private boolean penuhBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == '1') {
                    return false;
                }
            }
        }
        return true;
    }

    private void printBoard() {
        String[] colors = {
            "\u001B[37m",
            "\u001B[31m",
            "\u001B[94m",
            "\u001B[33m",
            "\u001B[95m",
            "\u001B[91m",
            "\u001B[32m",
            "\u001B[93m",
            "\u001B[34m",
            "\u001B[96m",
            "\u001B[92m",
            "\u001B[35m",
            "\u001B[36m",
            "\u001B[97m",
            "\u001B[90m",
            "\u001B[38;5;208m",
            "\u001B[105m",
            "\u001B[38;5;51m",
            "\u001B[106m",
            "\u001B[38;5;226m",
            "\u001B[101m",
            "\u001B[104m",
            "\u001B[102m",
            "\u001B[103m",
            "\u001B[107m",
            "\u001B[38;5;50m"
        };

        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '0') {
                    System.out.print(" ");
                } else {
                    int colorIndex = (Character.toUpperCase(cell) - 'A') % 26;
                    System.out.print(colors[colorIndex] + cell + "\u001B[0m");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}