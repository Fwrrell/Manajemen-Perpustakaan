package com.perpustakaan.ui;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.BukuKoleksiSpesial;
import com.perpustakaan.model.Member;

import com.perpustakaan.patterns.creational.builder.BukuBuilder;
import com.perpustakaan.patterns.creational.factory.BukuFactory;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;
import com.perpustakaan.patterns.structural.proxy.SistemManajemenProxy;
import com.perpustakaan.patterns.structural.decorator.BukuBaruDecorator;
import com.perpustakaan.patterns.structural.facade.PerpustakaanFacade;

import com.perpustakaan.repository.BukuRepository;

import java.sql.SQLException;
import java.util.List;

public class MainCLI {
    private final ConsoleUI ui;
    private final SistemManajemenProxy proxyKeamanan;
    private final PerpustakaanFacade facade;
    private boolean isRunning;
    private boolean isLoggedIn;

    public MainCLI() {
        this.ui = new ConsoleUI();
        this.proxyKeamanan = new SistemManajemenProxy();
        this.facade = new PerpustakaanFacade();
        this.isRunning = true;
        this.isLoggedIn = false;
    }

    public void mulai() {
        ui.tampilkanPesan("Memeriksa sistem dan database");
        try {
            KoneksiDB.getInstance();
            ui.tampilkanSukses("Sistem siap. Database terhubung.");

            while (isRunning) {
                if (!isLoggedIn) {
                    tampilkanMenuLogin();
                } else {
                    tampilkanMenuUtama();
                }
            }
        } catch (SQLException e) {
            ui.tampilkanError("Gagal terhubung ke database. Sistem dihentikan.");
            ui.tampilkanError("Detail: " + e.getMessage());
        }
    }

    private void tampilkanMenuLogin() {
        ui.tampilkanHeader("LOGIN PUSTAKAWAN");

        if (proxyKeamanan.isTerblokir()) {
            ui.tampilkanError("Sistem terkunci karena terlalu banyak percobaan gagal. Silakan restart aplikasi.");
            isRunning = false;
            return;
        }

        String username = ui.mintaInput("Username");
        String password = ui.mintaInput("Password");

        if (proxyKeamanan.autentikasi(username, password)) {
            isLoggedIn = true;
            ui.tampilkanSukses("Login berhasil!");
        } else {
            ui.tampilkanError("Username atau Password tidak valid. Silakan coba lagi.");
        }
    }

    private void tampilkanMenuUtama() {
        ui.tampilkanHeader("DASHBOARD PUSTAKAWAN");
        ui.tampilkanPesan("1. Kelola Buku");
        ui.tampilkanPesan("2. Kelola Member");
        ui.tampilkanPesan("3. Transaksi Peminjaman");
        ui.tampilkanPesan("4. Pencarian Buku");
        ui.tampilkanPesan("0. Logout");

        String pilihan = ui.mintaInput("Pilih menu (0-3)");

        switch (pilihan) {
            case "1":
                menuKelolaBuku();
                break;
            case "2":
                menuKelolaMember();
                break;
            case "3":
                ui.tampilkanPesan("[!] Menu Transaksi belum diimplementasikan.");
                break;
            case "4":
                menuPencarianBuku();
                break;
            case "0":
                isLoggedIn = false;
                ui.tampilkanSukses("Berhasil logout.");
                break;
            default:
                ui.tampilkanError("Pilihan tidak valid!");
        }
    }

    public void menuKelolaMember() {
        ui.tampilkanHeader("KELOLA MEMBER");
        ui.tampilkanPesan("1. Tambah Member Baru");
        ui.tampilkanPesan("2. Lihat Semua Member");
        ui.tampilkanPesan("3. Update Data Member");
        ui.tampilkanPesan("4. Hapus Member");
        ui.tampilkanPesan("5. Ubah Status Blokir");
        ui.tampilkanPesan("0. Kembali ke Dashboard");

        String pilihan = ui.mintaInput("Pilih menu (0-5)");
        switch (pilihan) {
            case "1":
                tambahMemberBaru();
                break;
            case "2":
                tampilkanSemuaMember();
                break;
            case "3":
                updateDataMember();
                break;
            case "4":
                hapusMember();
                break;
            case "5":
                ubahStatusBlokirMember();
                break;
            case "0":
                break;
            default:
                ui.tampilkanError("Pilihan tidak valid!");
        }
    }

    private void tampilkanSemuaMember() {
        ui.tampilkanHeader("DAFTAR MEMBER");
        List<Member> list = facade.ambilSemuaMember();
        if (list.isEmpty()) {
            ui.tampilkanPesan("Tidak ada member terdaftar.");
        } else {
            for (Member m : list) {
                ui.tampilkanPesan(m.toString());
            }
        }
    }

    private void tambahMemberBaru() {
        ui.tampilkanHeader("TAMBAH MEMBER BARU");
        String nama = ui.mintaInput("Nama Lengkap");
        String email = ui.mintaInput("Email");
        String phone = ui.mintaInput("Nomor Telepon");

        int idMember = facade.RegistrasiMember(nama, email, phone);
        if (idMember > 0) {
            ui.tampilkanSukses("Member baru berhasil didaftarkan dengan ID: " + idMember);
        } else {
            ui.tampilkanError("Gagal mendaftarkan member baru.");
        }
    }

    private void updateDataMember() {
        ui.tampilkanHeader("UPDATE DATA MEMBER");
        try {
            int id = Integer.parseInt(ui.mintaInput("Masukkan ID Member yang akan diupdate"));
            Member m = facade.cariMember(id);
            if (m == null) {
                ui.tampilkanError("Member tidak ditemukan!");
                return;
            }

            ui.tampilkanPesan("Data saat ini: " + m.toString());
            String nama = ui.mintaInput("Nama baru (Enter jika tetap)");
            String email = ui.mintaInput("Email baru (Enter jika tetap)");
            String phone = ui.mintaInput("Telepon baru (Enter jika tetap)");

            if (!nama.trim().isEmpty())
                m.setNama(nama);
            if (!email.trim().isEmpty())
                m.setEmail(email);
            if (!phone.trim().isEmpty())
                m.setTelpon(phone);

            if (facade.updateMember(m)) {
                ui.tampilkanSukses("Data member berhasil diperbarui!");
            }
        } catch (NumberFormatException e) {
            ui.tampilkanError("ID harus berupa angka!");
        }
    }

    private void ubahStatusBlokirMember() {
        ui.tampilkanHeader("UBAH STATUS BLOKIR");
        try {
            int id = Integer.parseInt(ui.mintaInput("Masukkan ID Member"));
            Member m = facade.cariMember(id);
            if (m == null) {
                ui.tampilkanError("Member tidak ditemukan!");
                return;
            }

            ui.tampilkanPesan("Status saat ini: " + (m.isBlocked() ? "TERBLOKIR" : "AKTIF"));
            String pilihan = ui.mintaInput("Blokir member? (y/n)");
            boolean status = pilihan.equalsIgnoreCase("y");

            if (facade.ubahStatusBlokirMember(id, status)) {
                ui.tampilkanSukses("Status blokir berhasil diubah!");
            }
        } catch (NumberFormatException e) {
            ui.tampilkanError("ID harus berupa angka!");
        }
    }

    private void hapusMember() {
        ui.tampilkanHeader("HAPUS MEMBER");
        try {
            int id = Integer.parseInt(ui.mintaInput("Masukkan ID Member yang akan dihapus"));
            String konfirmasi = ui.mintaInput("Anda yakin ingin menghapus member ini? (y/n)");
            if (konfirmasi.equalsIgnoreCase("y")) {
                if (facade.hapusMember(id)) {
                    ui.tampilkanSukses("Member berhasil dihapus dari sistem.");
                } else {
                    ui.tampilkanError("Gagal menghapus member.");
                }
            }
        } catch (NumberFormatException e) {
            ui.tampilkanError("ID harus berupa angka!");
        }
    }

    public void menuKelolaBuku() {
        ui.tampilkanHeader("KELOLA BUKU");
        ui.tampilkanPesan("1. Tambah Buku Baru");
        ui.tampilkanPesan("2. Tambah Buku Ekstra Detail");
        ui.tampilkanPesan("3. Tandai Label baru");
        ui.tampilkanPesan("0. Kembali ke Dashboard");

        String pilihan = ui.mintaInput("Pilih menu (0-1)");
        if ("1".equals(pilihan)) {
            tambahBukuBaru();
        } else if ("2".equals(pilihan)) {
            tambahBukuSpesial();
        } else if ("3".equals(pilihan)) {
            tandaiLabelBuku();
        }
    }

    public void menuPencarianBuku() {
        ui.tampilkanHeader("PENCARIAN BUKU");
        ui.tampilkanPesan("Cari berdasarkan:");
        ui.tampilkanPesan("1. Judul");
        ui.tampilkanPesan("2. Penulis");
        ui.tampilkanPesan("3. Genre");
        ui.tampilkanPesan("0. Kembali");

        String pilihan = ui.mintaInput("Pilih kriteria (0-3)");
        String kriteria = "";

        switch (pilihan) {
            case "1":
                kriteria = "judul";
                break;
            case "2":
                kriteria = "penulis";
                break;
            case "3":
                kriteria = "genre";
                break;
            case "0":
                return;
            default:
                ui.tampilkanError("Pilihan tidak valid!");
                return;
        }

        String kataKunci = ui.mintaInput("Masukkan kata kunci");
        List<Buku> hasil = facade.pencarianBuku(kriteria, kataKunci);

        if (hasil.isEmpty()) {
            ui.tampilkanPesan("Buku tidak ditemukan.");
        } else {
            ui.tampilkanSukses("Ditemukan " + hasil.size() + " buku:");
            for (Buku b : hasil) {
                ui.tampilkanPesan(b.toString());
            }
        }
    }

    private void tambahBukuBaru() throws IllegalArgumentException {
        ui.tampilkanHeader("TAMBAH BUKU BARU");

        try {
            String judul = ui.mintaInput("Judul Buku");
            String penulis = ui.mintaInput("Penulis");
            String genre = ui.mintaInput("Genre / Subjek");
            String jenis = ui.mintaInput("Jenis Buku (Fiksi/Jurnal/Pelajaran)");

            String info = null;
            if (!jenis.equalsIgnoreCase("Fiksi")) {
                info = ui.mintaInput("Info Tambahan (ID Jurnal / Subjek Pelajaran)");
            }

            String batasStr = ui.mintaInput("Batas Hari Peminjaman (angka)");
            int batasHari = Integer.parseInt(batasStr);

            String hargaStr = ui.mintaInput("Harga Beli (angka)");
            double hargaBeli = Double.parseDouble(hargaStr);

            BukuFactory factory = new BukuFactory();
            Buku bukuBaru = factory.buatBuku(jenis, judul, penulis, genre, batasHari, hargaBeli, info);

            BukuRepository repo = new BukuRepository();
            boolean sukses = repo.tambahBuku(bukuBaru);

            if (sukses) {
                ui.tampilkanSukses(
                        "Buku '" + judul + "' (" + bukuBaru.getJenisBuku() + ") berhasil ditambahkan dengan ID: "
                                + bukuBaru.getIdBuku());
            }
        } catch (NumberFormatException e) {
            ui.tampilkanError("Input angka tidak valid! Pastikan Batas Hari dan Harga Beli diisi angka.");
        } catch (RuntimeException e) {
            ui.tampilkanError(e.getMessage());
        }
    }

    private void tambahBukuSpesial() {
        ui.tampilkanHeader("TAMBAH BUKU SPESIAL (KOLEKSI)");
        ui.tampilkanPesan("[!] Tekan ENTER langsung jika data opsional tidak diketahui.");

        try {
            // Data Wajib
            String judul = ui.mintaInput("Judul Buku");
            String penulis = ui.mintaInput("Penulis");
            String genre = ui.mintaInput("Kategori/Genre");

            int batasHari = Integer.parseInt(ui.mintaInput("Batas Hari Peminjaman (angka)"));
            double hargaBeli = Double.parseDouble(ui.mintaInput("Harga Beli (angka)"));

            BukuBuilder builder = new BukuBuilder(judul, penulis, genre, batasHari, hargaBeli);

            // Data Opsional
            String isbn = ui.mintaInput("Nomor ISBN (opsional)");
            if (!isbn.trim().isEmpty())
                builder.setIsbn(isbn);

            String penerbit = ui.mintaInput("Nama Penerbit (opsional)");
            if (!penerbit.trim().isEmpty())
                builder.setPenerbit(penerbit);

            String tahun = ui.mintaInput("Tahun Terbit (opsional)");
            if (!tahun.trim().isEmpty())
                builder.setTahunTerbit(tahun);

            String rak = ui.mintaInput("Lokasi Rak Fisik (opsional)");
            if (!rak.trim().isEmpty())
                builder.setLokasiRak(rak);

            String edisi = ui.mintaInput("Edisi Cetakan (opsional)");
            if (!edisi.trim().isEmpty())
                builder.setEdisi(edisi);

            BukuKoleksiSpesial bukuSpesial = builder.build();

            BukuRepository repo = new BukuRepository();
            boolean sukses = repo.tambahBuku(bukuSpesial);

            if (sukses) {
                ui.tampilkanSukses("Buku Koleksi Spesial '" + judul + "' berhasil didaftarkan dengan ID: "
                        + bukuSpesial.getIdBuku());
            }

        } catch (NumberFormatException e) {
            ui.tampilkanError("Input angka tidak valid!");
        } catch (Exception e) {
            ui.tampilkanError("Terjadi kesalahan: " + e.getMessage());
        }
    }

    private void tandaiLabelBuku() {
        ui.tampilkanHeader("ATUR LABEL BUKU");
        try {
            int id = Integer.parseInt(ui.mintaInput("Masukkan ID Buku"));

            BukuRepository repo = new BukuRepository();
            Buku buku = repo.cariBuku(id);
            if (buku == null) {
                ui.tampilkanError("Buku tidak ditemukan!");
                return;
            }

            ui.tampilkanPesan("Buku  : " + buku.getJudul());
            ui.tampilkanPesan("Label Buku : ");
            ui.tampilkanPesan("1. Baru");
            ui.tampilkanPesan("2. Populer");
            ui.tampilkanPesan("3. Langka");
            ui.tampilkanPesan("4. Promo");
            ui.tampilkanPesan("0. Batal");

            String label = ui.mintaInput("Pilih Label Buku");
            if (label.equals("0")) {
                return;
            }

            String aksi = ui.mintaInput("Tandai label ini? (y = tandai / t = lepas)");
            boolean status = aksi.equals("y");

            boolean sukses;
            switch (label) {
                case "1":
                    sukses = facade.tandaiBaru(id, status);
                    break;
                case "2":
                    sukses = facade.tandaiPopuler(id, status);
                    break;
                case "3":
                    sukses = facade.tandaiLangka(id, status);
                    break;
                case "4":
                    sukses = facade.tandaiPromo(id, status);
                    break;
                default:
                    ui.tampilkanError("Pilihan tidak valid!");
                    return;
            }

            if (sukses) {
                if (status) {
                    ui.tampilkanSukses("Label berhasil ditambahkan ke buku!");
                } else {
                    ui.tampilkanSukses("Label berhasil dilepas dari buku!");
                }
            } else {
                ui.tampilkanError("Gagal memperbarui label buku.");
            }

        } catch (NumberFormatException e) {
            ui.tampilkanError("Input angka tidak valid!");
        }
    }
}