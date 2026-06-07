package com.perpustakaan;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/perpustakaan_db?useSSL=false&serverTimezone=Asia/Jakarta";
        String user = "root";
        String pass = "";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("✅ Koneksi berhasil! Database: " + conn.getCatalog());
        } catch (Exception e) {
            System.out.println("❌ Koneksi gagal: " + e.getMessage());
        }
    }
}
