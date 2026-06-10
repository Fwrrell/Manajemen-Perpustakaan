package com.perpustakaan.ui;

import com.perpustakaan.model.Buku;
import com.perpustakaan.patterns.creational.factory.BukuFactory;
import com.perpustakaan.patterns.creational.singleton.KoneksiDB;
import com.perpustakaan.patterns.structural.proxy.SistemManajemenProxy;

import java.sql.SQLException;

public class MainCLI {
    private final ConsoleUI ui;
    private final SistemManajemenProxy proxyKeamanan;
    private boolean isRunning;
    private boolean isLoggedIn;

    public MainCLI() {
        this.ui = new ConsoleUI();
        this.proxyKeamanan = new SistemManajemenProxy();
        this.isRunning = true;
        this.isLoggedIn = false;
    }

    public void mulai() {
        ui.tampilkanPesan("Memeriksa sistem dan database");
        try {
            KoneksiDB.getInstance();
            ui.tampilkanSukses("Sistem siap. Database terhubung.");

            while (isRunning) {
                tampilkanMenuUtama();
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
                ui.tampilkanPesan("[!] Menu Kelola Member belum diimplementasikan.");
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

    public void menuKelolaBuku() {
        ui.tampilkanHeader("KELOLA BUKU");
        ui.tampilkanPesan("1.   Tambah Buku Baru");
        ui.tampilkanPesan("0.   Kembali ke Dashboard");

        String pilihan = ui.mintaInput("Pilih menu (0-1)");
        if ("1".equals(pilihan)) {
            tambahBukuBaru();
        }
    }

    public void menuPencarianBuku() {
        ui.tampilkanHeader("Menu Pencarian");
    }

    private void tambahBukuBaru() throws IllegalArgumentException {
        ui.tampilkanHeader("TAMBAH BUKU BARU");

        try {
            String idBuku = ui.mintaInput("ID Buku (ex: BK-006)");
            String judul = ui.mintaInput("Judul Buku");
            String penulis = ui.mintaInput("Penulis");
            String genre = ui.mintaInput("Genre / Subjek");
            String jenis = ui.mintaInput("Jenis Buku (Fiksi/Jurnal/Pelajaran)");

            String batasStr = ui.mintaInput("Batas Hari Peminjaman (angka)");
            int batasHari = Integer.parseInt(batasStr);

            String hargaStr = ui.mintaInput("Harga Beli (angka)");
            double hargaBeli = Double.parseDouble(hargaStr);

            BukuFactory factory = new BukuFactory();
            // Buku bukuBaru = factory.buatBuku(idBuku, judul, penulis, genre, batasHari, hargaBeli, jenis);

            

            // if (sukses) {
            //     ui.tampilkanSukses(
            //             "Buku '" + judul + "' (" + bukuBaru.getJenisBuku() + ") berhasil ditambahkan ke database!");
            // }
        } catch (NumberFormatException e) {
            ui.tampilkanError("Input angka tidak valid! Pastikan Batas Hari dan Harga Beli diisi angka.");
        } catch (RuntimeException e) {
            ui.tampilkanError(e.getMessage());
        }
    }
}