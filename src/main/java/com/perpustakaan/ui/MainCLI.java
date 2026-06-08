package com.perpustakaan.ui;

import com.perpustakaan.patterns.creational.singleton.KoneksiDB;

import java.util.Scanner;

public class MainCLI {
    private Scanner scanner;

    public MainCLI() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Memeriksa sistem...");
        if (KoneksiDB.getInstance().getConnection() == null) {
            System.out.println("Sistem dihentikan: Database tidak terhubung.");
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n========================================");
            System.out.println(" 📚 SISTEM MANAJEMEN PERPUSTAKAAN (ADMIN) ");
            System.out.println("========================================");
            System.out.println("1. Kelola Buku (Tambah/Cari)");
            System.out.println("2. Kelola Member (Daftar Member Baru)");
            System.out.println("3. Transaksi Peminjaman");
            System.out.println("4. Transaksi Pengembalian");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu (0-4): ");

            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    menuKelolaBuku();
                    break;
                case "2":
                    menuKelolaMember();
                    break;
                case "3":
                    menuPeminjaman();
                    break;
                case "4":
                    menuPengembalian();
                    break;
                case "0":
                    System.out.println("Terima kasih!");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }

    private void menuKelolaBuku() {
        System.out.println("\n-- Menu Kelola Buku --");
        System.out.println("(Fitur Factory & Strategy akan masuk ke sini)");
    }

    private void menuKelolaMember() {
        System.out.println("\n-- Menu Kelola Member --");
        System.out.println("(Fitur pendaftaran member akan masuk ke sini)");
    }

    private void menuPeminjaman() {
        System.out.println("\n-- Menu Peminjaman --");
        System.out.println("(Fitur peminjaman dengan State Pattern akan masuk ke sini)");
    }

    private void menuPengembalian() {
        System.out.println("\n-- Menu Pengembalian --");
        System.out.println("(Fitur pengembalian dan denda akan masuk ke sini)");
    }
}