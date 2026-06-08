package com.perpustakaan.patterns.structural.proxy;

public class SistemManajemenAsli implements SistemManajemen {

    @Override
    public boolean autentikasi(String username, String password) {
        return "admin".equals(username) && "admin123".equals(password);
    }
}