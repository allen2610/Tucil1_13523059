import java.util.*;

public class Piece {
    private char name;
    private char[][] shape; 
    private List<char[][]> versions; 

    public Piece(char name, List<String> inputShape) {
        this.name = name;
        this.shape = changeToArray(inputShape);
    
        if (!cekTerhubung()) {
            System.out.println("Piece " + name + " tidak dapat digunakan karena tidak terhubung!");
            return;
        }

        transform();
    }

    public char getName() {
        return name;
    }

    public char[][] getShape() {
        return shape;
    }

    public List<char[][]> getPieceVersions() {
        return versions;
    }

    private char[][] changeToArray(List<String> inputShape) {
        int rows = inputShape.size();
        int cols = 0;

        for (String row : inputShape) {
            cols = Math.max(cols, row.length());
        }

        char[][] hasil = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            String row = inputShape.get(r);
            for (int c = 0; c < cols; c++) {
                hasil[r][c] = (c < row.length()) ? row.charAt(c) : ' ';
            }
        }
        return hasil;
    }

    private boolean cekTerhubung() {
        int rows = shape.length;
        int cols = shape[0].length;
        boolean[][] udahCek = new boolean[rows][cols];
        
        int[][] arah = {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}
        };

        int startX = -1, startY = -1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (shape[r][c] == name) {
                    startX = r;
                    startY = c;
                    break;
                }
            }
            if (startX != -1) break;
        }

        if (startX == -1) return false; 

        Queue<int[]> antrian = new ArrayDeque<>();
        antrian.add(new int[]{startX, startY});
        udahCek[startX][startY] = true;

        int count = 0, hitung = 0;
        for (char[] row : shape) {
            for (char cell : row) {
                if (cell == name) hitung++;
            }
        }

        while (!antrian.isEmpty()) {
            int[] curr = antrian.poll();
            count++;

            for (int i = 0; i < arah.length; i++) {
                int newX = curr[0] + arah[i][0];
                int newY = curr[1] + arah[i][1];
            
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols &&
                    !udahCek[newX][newY] && shape[newX][newY] == name) {
                    udahCek[newX][newY] = true;
                    antrian.add(new int[]{newX, newY});
                }
            }
        }

        return count == hitung;
    }

    private char[][] diBalik(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] terbalik = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                terbalik[r][cols - 1 - c] = matrix[r][c];
            }
        }
        return terbalik;
    }
    
    private char[][] putar(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] terputar = new char[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                terputar[c][rows - 1 - r] = matrix[r][c];
            }
        }
        return terputar;
    }

    public void transform() {
        versions = new ArrayList<>();
        char[][] now = shape;

        for (int i = 0; i < 4; i++) {  
            now = putar(now);
            versions.add(now);
        }

        now = diBalik(shape);
        versions.add(now);
        for (int i = 0; i < 3; i++) {
            now = putar(now);
            versions.add(now);
        }
    }
}