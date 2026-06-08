package com.perpustakaan.patterns.structural.proxy;

public class SistemManajemenProxy implements SistemManajemen {
    private SistemManajemenAsli sistemAsli;
    private int percobaanGagal = 0;

    @Override
    public boolean autentikasi(String username, String password) {
        // Blokir sementara jika gagal 3 kali
        if (percobaanGagal >= 3) {
            return false;
        }

        if (sistemAsli == null) {
            sistemAsli = new SistemManajemenAsli();
        }

        boolean isAksesDiberikan = sistemAsli.autentikasi(username, password);

        if (!isAksesDiberikan) {
            percobaanGagal++;
        } else {
            percobaanGagal = 0;
        }

        return isAksesDiberikan;
    }

    public boolean isTerblokir() {
        return percobaanGagal >= 3;
    }
}