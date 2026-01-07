# ChessGame
Project OOP

Oleh: Hansel Kristanzen Siswanto - 2802495505
Deskripsi Singkat
The Chess adalah aplikasi permainan catur berbasis GUI (Graphical User Interface) yang dibangun menggunakan bahasa pemrograman Java. Proyek ini dibuat untuk memenuhi tugas akhir mata kuliah Pemrograman Berorientasi Objek (OOP).
Aplikasi ini tidak hanya menyajikan permainan catur standar, tetapi juga dilengkapi dengan fitur Blitz Mode (Timer), pencatatan riwayat pertandingan ke dalam file CSV, serta validasi langkah bidak yang akurat.
Fitur Utama (4+ Fitur)
Sesuai kriteria penilaian, aplikasi ini memiliki lebih dari 4 fitur utama:
Dua Mode Permainan:
Unlimited Mode: Bermain santai tanpa batasan waktu.
Blitz Mode: Bermain kompetitif dengan timer mundur (10 menit per pemain).
Sistem Validasi Gerak (Rule Engine): Setiap bidak (Rook, Knight, Bishop, Queen, King, Pawn) memiliki logika validasi langkahnya sendiri sesuai aturan catur internasional.
Riwayat Pertandingan (Match History):
Mencatat setiap langkah (Waktu, Giliran, Bidak, Posisi Awal, Posisi Akhir) ke dalam memori.
File Handling: Menyimpan riwayat permanen ke dalam file eksternal chess_log.csv.
Fitur untuk melihat kembali riwayat pertandingan sebelumnya melalui menu GUI.
Fitur Surrender & Reset: Pemain dapat menyerah di tengah permainan atau mereset papan untuk memulai ulang tanpa menutup aplikasi.
Validasi Input & Exception Handling: Mencegah input nama kosong dan menangani error saat membaca/menulis file.
Struktur Program & Daftar Class (Minimal 10 Class)
Proyek ini dirancang secara modular dengan total 14 Class (termasuk Inner Classes), jauh melampaui batas minimal 10 class yang disyaratkan.
1. Abstract & Parent Classes
ChessGameProject (Main Class): Entry point aplikasi.
Piece (Abstract Class): Template dasar untuk semua bidak catur.
2. Concrete Classes (Inheritance - Bidak Catur)
King: Logika gerak Raja.
Queen: Logika gerak Ratu.
Rook: Logika gerak Benteng.
Bishop: Logika gerak Gajah/Peluncur.
Knight: Logika gerak Kuda.
Pawn: Logika gerak Pion.
3. Logic & Helper Classes
MoveValidator: Kelas utilitas statis untuk mengecek apakah jalur bidak terhalang atau tidak.
FileManager: Menangani operasi File Input/Output (Membaca dan menulis chess_log.csv).
4. GUI & Interface Classes
MainMenu (extends JFrame): Tampilan awal menu permainan.
ChessBoard (extends JFrame): Tampilan papan catur dan logika permainan inti.
HistoryViewer (extends JFrame): Jendela untuk melihat tabel riwayat CSV.
Cell (extends JButton): Representasi visual kotak pada papan catur.
Penerapan Konsep OOP
1. Encapsulation
Variabel data dibuat private dan diakses melalui method public (Getter).
Contoh Code: Di class Piece:
private boolean isWhite;
private String name;
public boolean isWhite() { return isWhite; }


2. Inheritance 
Menggunakan keyword extends untuk mewariskan sifat dari Parent Class ke Child Class.
Contoh: Semua bidak (e.g., Rook, Knight) adalah turunan dari class Piece.
Contoh GUI: ChessBoard dan MainMenu adalah turunan dari JFrame.
3. Polymorphism
Menggunakan @Override untuk mengubah perilaku method parent sesuai kebutuhan spesifik child class.
Contoh: Setiap bidak memiliki implementasi method isValidMove() dan getSymbol() yang berbeda-beda meskipun nama methodnya sama.
// Di class Rook
@Override
public boolean isValidMove(...) { ...logika benteng... }

// Di class Knight
@Override
public boolean isValidMove(...) { ...logika kuda... }


4. Abstraction
Menyembunyikan detail implementasi yang kompleks menggunakan abstract class dan abstract method.
Contoh: Class Piece tidak bisa diinstansiasi langsung dan memaksa turunannya untuk mengimplementasikan method abstrak:
public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Piece[][] board);


File Handling & Koleksi Data
File I/O: Menggunakan BufferedWriter dan BufferedReader (Java IO) untuk menyimpan data log permainan ke file chess_log.csv. Ini memastikan data tetap ada meskipun aplikasi ditutup.
Collections:
ArrayList<String>: Digunakan untuk menyimpan riwayat langkah sementara di memori.
Vector: Digunakan di DefaultTableModel untuk menampilkan data di JTable.
Exception Handling: Menggunakan blok try-catch untuk menangani IOException saat akses file dan IllegalArgumentException saat validasi input nama pemain.
Cara Menjalankan
Pastikan Java Development Kit (JDK) sudah terinstall (Minimal JDK 8).
Buka IDE
Open folder ChessGame
Buka file src/chess7/main/Main.java
Klik tombol Run
Pilih menu "Play Unlimited" atau "Play Blitz" untuk memulai.
Masukkan nama pemain
Klik Piece lalu klik kotak tujuan yang valid
