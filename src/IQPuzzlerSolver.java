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
        transformBoard();
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
                        removeBlock(shape, r, c);
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

    private void removeBlock(char[][] shape, int row, int col) {
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
            "\u001B[37m",      // White
            "\u001B[31m",      // Red
            "\u001B[94m",      // Bright Blue
            "\u001B[33m",      // Yellow
            "\u001B[95m",      // Bright Magenta
            "\u001B[91m",      // Bright Red
            "\u001B[32m",      // Green
            "\u001B[93m",      // Bright Yellow
            "\u001B[34m",      // Blue
            "\u001B[96m",      // Bright Cyan
            "\u001B[92m",      // Bright Green
            "\u001B[35m",      // Magenta
            "\u001B[36m",      // Cyan
            "\u001B[97m",      // Bright White
            "\u001B[90m",      // Dark Gray
            "\u001B[38;5;208m", // Orange
            "\u001B[105m",     // Bright Purple (Background)
            "\u001B[38;5;51m", // Light Blue
            "\u001B[106m",     // Bright Green (Background)
            "\u001B[38;5;226m", // Bright Yellow
            "\u001B[101m",     // Bright Red (Background)
            "\u001B[104m",     // Bright Blue (Background)
            "\u001B[102m",     // Bright Green (Background)
            "\u001B[103m",     // Bright Yellow (Background)
            "\u001B[107m",     // Bright White (Background)
            "\u001B[38;5;50m"  // Neon Green
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

    private void transformBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == 'X') {
                    board[r][c] = '1';
                } else {
                    board[r][c] = '0';
                }
            }
        }
    }
}