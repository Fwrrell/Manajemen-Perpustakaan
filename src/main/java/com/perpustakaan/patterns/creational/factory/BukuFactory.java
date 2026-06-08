package com.perpustakaan.patterns.creational.factory;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.BukuFiksi;
import com.perpustakaan.model.BukuJurnal;
import com.perpustakaan.model.BukuPelajaran;

public class BukuFactory {
    public Buku buatBuku(String idBuku, String judul, String penulis, String genre,
            int batasHariPeminjaman, double hargaBeli, String jenisBuku) {
        if (jenisBuku.equals("Fiksi")) {
            return new BukuFiksi(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
        } else if (jenisBuku.equals("Jurnal")) {
            return new BukuJurnal(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli, "JurnalID-" + idBuku);
        } else if (jenisBuku.equals("Pelajaran")) {
            return new BukuPelajaran(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli, genre);
        } else {
            return new Buku(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
        }
    }
}
