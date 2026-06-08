package com.perpustakaan.patterns.creational.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private static KoneksiDB instance;
    private Connection connection;

    private String url = "jdbc:mysql://localhost:3306/perpustakaan_db?useSSL=false&serverTimezone=Asia/Jakarta";
    private String username = "root";
    private String password = "";

    private KoneksiDB() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Gagal terhubung ke database: " + e.getMessage());
        }
    }

    public static KoneksiDB getInstance() {
        if (instance == null) {
            instance = new KoneksiDB();
        } else {
            try {
                if (instance.getConnection().isClosed()) {
                    instance = new KoneksiDB();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}