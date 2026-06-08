package com.perpustakaan;

import com.perpustakaan.patterns.creational.singleton.KoneksiDB;
import com.perpustakaan.ui.MainCLI;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Connection conn = KoneksiDB.getInstance().getConnection();
        if (conn != null) {
            System.out.println("Database Terhubung!");

            MainCLI cli = new MainCLI();
            cli.start();
        } else {
            System.out.println("Aplikasi berhenti karena gagal terhubung ke database.");
        }
    }
}