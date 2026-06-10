# Sistem Manajemen Perpustakaan (CLI)

## Fitur Utama

Aplikasi ini sudah mendukung operasional dasar perpustakaan, antara lain:

- **Dashboard Statistik**: Lihat total koleksi dan jumlah peminjam aktif secara real-time.
- **Manajemen Buku**: Tambah koleksi baru (Fiksi, Jurnal, Pelajaran) dengan sistem ID otomatis.
- **Koleksi Spesial**: Fitur pencatatan detail ekstra (ISBN, Rak, Edisi) menggunakan _Builder Pattern_.
- **Label Dinamis**: Memberi tanda pada buku (Langka, Populer, Promo) tanpa merubah data asli.
- **Manajemen Member**: Pendaftaran anggota baru dan sistem blokir otomatis.
- **Pencarian Pintar**: Cari buku berdasarkan Judul, Penulis, atau Genre.
- **Sistem Login Aman**: Proteksi akun pustakawan dengan sistem pembatasan percobaan gagal.

## 🛠️ Tech Stack

- **Bahasa**: Java (JDK 11+)
- **Database**: MySQL
- **Library**: JDBC (untuk koneksi database)

## 🏗️ Design Patterns yang Digunakan

Sebagai inti dari tugas ADPL, kami menerapkan beberapa pattern berikut:

1.  **Facade**: Menyederhanakan akses ke berbagai fungsi sistem melalui satu gerbang utama.
2.  **Proxy**: Menangani keamanan login (autentikasi).
3.  **Decorator**: Menambahkan label/status tambahan pada buku secara fleksibel.
4.  **Factory**: Standarisasi pembuatan berbagai jenis objek buku.
5.  **Builder**: Memudahkan pembuatan objek buku koleksi spesial yang punya banyak detail.
6.  **Singleton**: Memastikan koneksi database hanya dibuat satu kali (hemat resource).
7.  **State**: Mengelola status buku (Tersedia/Dipinjam) dengan lebih elegan.
8.  **Strategy**: Memungkinkan pergantian algoritma pencarian buku saat aplikasi berjalan.

## 🚀 Cara Menjalankan

1.  **Persiapan Database**:
    - Import file `sql/schema.sql` ke MySQL.
    - Sesuaikan konfigurasi database di class `KoneksiDB`.
2.  **Compile & Run**:
    - Pastikan driver MySQL JDBC sudah terpasang.
    - Jalankan `App.java` atau `MainCLI.java`.
3.  **Login Default**:
    - **Username**: `admin`
    - **Password**: `admin123`
