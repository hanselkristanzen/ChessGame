package chess7.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class FileManager {
    private static final String CSV_FILENAME = "chess_log.csv";

    public static void initFile() {
        File f = new File(CSV_FILENAME);
        if(!f.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILENAME))) {
                writer.write("Waktu,Giliran,Bidak,Dari,Ke,Keterangan");
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error inisialisasi file: " + e.getMessage());
            }
        }
    }

    public static void saveToCSV(String turn, String piece, String from, String to, String note) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILENAME, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String line = String.format("%s,%s,%s,%s,%s,%s", 
                timestamp, turn, piece, from, to, note);
            
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data ke file: " + e.getMessage(), "IO Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static DefaultTableModel readHistory() {
        Vector<String> columns = new Vector<>();
        columns.add("Waktu"); columns.add("Giliran"); columns.add("Bidak");
        columns.add("Dari"); columns.add("Ke"); columns.add("Keterangan");

        Vector<Vector<String>> data = new Vector<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILENAME))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; } // Skip header
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    Vector<String> row = new Vector<>();
                    for (String part : parts) row.add(part);
                    data.add(row);
                }
            }
        } catch (IOException e) {
            return new DefaultTableModel(null, columns); 
        }

        return new DefaultTableModel(data, columns);
    }
}