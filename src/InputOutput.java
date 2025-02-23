import java.io.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InputOutput {
    public static List<Piece> bacaInput(String fileName, int[] ukuran, List<String> newBoard) throws IOException {
        List<Piece> pieces = new ArrayList<>();
        Map<Character, Boolean> pieceTerpakai = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();

            if (line == null) {
                throw new IllegalArgumentException("File input tidak valid, tidak memiliki isi.");
            }

            String[] input = line.split(" ");
            if (input.length != 3) {
                throw new IllegalArgumentException("Input ukuran pada file tidak lengkap/sesuai.");
            }

            int lebar = Integer.parseInt(input[0]);
            int tinggi = Integer.parseInt(input[1]);
            int jumlahPieces = Integer.parseInt(input[2]);

            if (lebar <= 0 || tinggi <= 0 || jumlahPieces <= 0) {
                throw new IllegalArgumentException("Ukuran board dan jumlah pieces yang ada pada file input harus lebih besar dari 0.");
            }

            ukuran[0] = lebar;
            ukuran[1] = tinggi;

            String boardType = reader.readLine();
            if (boardType == null) throw new IllegalArgumentException("File input tidak mengandung jenis board.");

            if ("DEFAULT".equals(boardType)) {
                for (int i = 0; i < tinggi; i++) {
                    newBoard.add("X".repeat(lebar));
                }
                List<String> pieceSekarang = new ArrayList<>();
                Character nameSekarang = null;

                String lineSekarang;
                while ((lineSekarang = reader.readLine()) != null) {
                    if (lineSekarang.trim().isEmpty()) {
                        throw new IllegalArgumentException("Ada baris piece yang kosong pada file input.");
                    }

                    char name = findName(lineSekarang);
                    if (nameSekarang == null || name != nameSekarang) {
                        if (nameSekarang != null) {
                            pieces.add(new Piece(nameSekarang, pieceSekarang));
                            pieceTerpakai.put(nameSekarang, true);
                        }

                        if (pieceTerpakai.getOrDefault(name, false)) {
                            throw new IllegalArgumentException("Ada dua piece dengan nama " + name + ".");
                        }

                        nameSekarang = name;
                        pieceSekarang = new ArrayList<>();
                    }
                    pieceSekarang.add(lineSekarang);
                }

                if (nameSekarang != null) {
                    pieces.add(new Piece(nameSekarang, pieceSekarang));
                }

                if (pieces.size() != jumlahPieces) {
                    throw new IllegalArgumentException("Jumlah piece tidak sesuai dengan input awal.");
                }
                return pieces;
            } else {
                throw new IllegalArgumentException("Maaf, program ini hanya mendukung board jenis 'DEFAULT':(");
            }
        }
    }

    private static char findName(String line) {
        for (char c : line.toCharArray()) {
            if (c != ' ') return c;
        }
        throw new IllegalArgumentException("Pieces dalam file tidak valid.");
    }

    public static char[][] createBoard(List<String> boardRow) {
        int tinggi = boardRow.size();
        int lebar = boardRow.get(0).length();
        char[][] board = new char[tinggi][lebar];

        for (int r = 0; r < tinggi; r++) {
            for (int c = 0; c < lebar; c++) {
                board[r][c] = boardRow.get(r).charAt(c);
            }
        }
        return board;
    }

    public static void saveBoard(char[][] board, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (char[] row : board) {
                StringBuilder rowString = new StringBuilder();
                for (char now : row) {
                    if (now == '0') {
                        rowString.append(" ");
                    } else {
                        rowString.append(now);
                    }
                }
                writer.write(rowString.toString());
                writer.newLine();
            }
            System.out.println("Solusi berhasil tersimpan dengan nama " + fileName + ".");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan dalam bentuk file." + e.getMessage());
        }
    }
}