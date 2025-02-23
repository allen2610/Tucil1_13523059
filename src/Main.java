import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nama file input (tambahkan .txt): ");
        String fileName = scanner.nextLine().trim();

        fileName = "../test/input/" + fileName;

        int[] ukuran = new int[2];
        List<String> newBoard = new ArrayList<>();

        try {
            if (!new File(fileName).exists()) {
                throw new FileNotFoundException("File dengan nama " + fileName + " tidak dapat ditemukan. Mohon cek kembali nama file atau penempatannya.");
            }

            List<Piece> pieces = InputOutput.bacaInput(fileName, ukuran, newBoard);
            char[][] board = InputOutput.createBoard(newBoard);
            IQPuzzlerSolver solver = new IQPuzzlerSolver(board, pieces);
            boolean solved = solver.solvePuzzle();

            if (solved) {
                while (true) {
                    System.out.print("Apakah Anda ingin menyimpan jawaban Anda dalam file? (y/n): ");
                    String saveTxt = scanner.nextLine().trim().toLowerCase();
    
                    if (saveTxt.equals("y")) {
                        String txtFilename;
                        while (true) {
                            System.out.print("Nama file output (tambahkan .txt): ");
                            txtFilename = scanner.nextLine().trim();

                            if (txtFilename.matches("^[a-zA-Z0-9_\\-]+\\.txt$")) {
                                break;
                            } else {
                                System.out.println("Mohon akhiri nama file dengan .txt.");
                            }
                        }
                        txtFilename = "../test/output/" + txtFilename;
                        InputOutput.saveBoard(board, txtFilename);
                        break;
                    } else if (saveTxt.equals("n")) {
                        break;
                    } else {
                        System.out.println("Mohon input y atau n.");
                    }
                }
    
            }
        } catch (IOException e) {
            System.out.println("Mohon maaf, kesalahan telah terjadi pada program. " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Mohon maaf, kesalahan telah terjadi pada program. " + e.getMessage());
        }
    }
}