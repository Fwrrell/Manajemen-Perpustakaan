package com.perpustakaan.patterns.creational.factory;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.BukuFiksi;
import com.perpustakaan.model.BukuJurnal;
import com.perpustakaan.model.BukuPelajaran;

public class BukuFactory {
    public Buku buatBuku(String tipe, String judul, String penulis, String genre,int batasHariPeminjaman, double hargaBeli, String info) {
        int id = -1;
        if (tipe.equalsIgnoreCase("Fiksi")) {
            return new BukuFiksi(id, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
        } else if (tipe.equalsIgnoreCase("Jurnal")) {
            return new BukuJurnal(id, judul, penulis, genre, batasHariPeminjaman, hargaBeli, info);
        } else if (tipe.equalsIgnoreCase("Pelajaran")) {
            return new BukuPelajaran(id, judul, penulis, genre, batasHariPeminjaman, hargaBeli, info);
        } else {
            throw new IllegalArgumentException("Tipe buku '" + tipe + "' tidak valid di sistem.");
        }
    }
}