package com.perpustakaan.model;

public class BukuFiksi extends Buku {

    public BukuFiksi(int idBuku, String judul, String penulis, String genre, int batasHariPeminjaman, double hargaBeli) {
        super(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
    }

    @Override
    public String getJenisBuku() {
        return "Fiksi";
    }
}
