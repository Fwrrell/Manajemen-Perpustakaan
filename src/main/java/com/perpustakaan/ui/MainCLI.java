package com.perpustakaan.ui;

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
        ui.tampilkanPesan("0. Logout");

        String pilihan = ui.mintaInput("Pilih menu (0-3)");

        switch (pilihan) {
            case "1":
                ui.tampilkanPesan("[!] Menu Kelola Buku belum diimplementasikan.");
                break;
            case "2":
                ui.tampilkanPesan("[!] Menu Kelola Member belum diimplementasikan.");
                break;
            case "3":
                ui.tampilkanPesan("[!] Menu Transaksi belum diimplementasikan.");
                break;
            case "0":
                isLoggedIn = false;
                ui.tampilkanSukses("Berhasil logout.");
                break;
            default:
                ui.tampilkanError("Pilihan tidak valid!");
        }
    }
}