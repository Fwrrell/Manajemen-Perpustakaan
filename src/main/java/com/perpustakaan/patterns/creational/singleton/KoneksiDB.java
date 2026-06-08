package com.perpustakaan.patterns.creational.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private static KoneksiDB instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/perpustakaan_db?useSSL=false&serverTimezone=Asia/Jakarta";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private KoneksiDB() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static KoneksiDB getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new KoneksiDB();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}