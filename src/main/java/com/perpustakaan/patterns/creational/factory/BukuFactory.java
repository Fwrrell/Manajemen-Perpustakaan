package com.perpustakaan.patterns.creational.factory;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.BukuFiksi;
import com.perpustakaan.model.BukuJurnal;
import com.perpustakaan.model.BukuPelajaran;

public class BukuFactory {
    public Buku buatBuku(String judul, String penulis, String jenisBuku) {
        if (jenisBuku.equals("Fiksi")) {
            return new BukuFiksi("1", judul, penulis, jenisBuku, 0, 0);
        } else if (jenisBuku.equals("Jurnal")) {
            return new BukuJurnal(judul, jenisBuku, penulis, penulis, 0, null, jenisBuku);
        } else {
            return new BukuPelajaran(jenisBuku, judul, penulis, penulis, 0, 0, jenisBuku);
        }
    }
}
