package com.perpustakaan.patterns.creational.builder;

import com.perpustakaan.model.Buku;
import com.perpustakaan.patterns.creational.factory.BukuFactory;

public class BukuBuilder {
    private String idBuku;
    private String judul;
    private String penulis;
    private String genre;
    private int batasHariPeminjaman;
    private double hargaBeli;
    private String tipeBuku;

    public BukuBuilder setIdBuku(String idBuku) {
        this.idBuku = idBuku;
        return this;
    }

    public BukuBuilder setJudul(String judul) {
        this.judul = judul;
        return this;
    }

    public BukuBuilder setPenulis(String penulis) {
        this.penulis = penulis;
        return this;
    }

    public BukuBuilder setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public BukuBuilder setBatasHariPeminjaman(int batasHariPeminjaman) {
        this.batasHariPeminjaman = batasHariPeminjaman;
        return this;
    }

    public BukuBuilder setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
        return this;
    }

    public BukuBuilder setTipeBuku(String tipeBuku) {
        this.tipeBuku = tipeBuku;
        return this;
    }

    public Buku build() {
        // Jika tipe buku ditentukan, serahkan pembuatannya ke Factory
        if (tipeBuku != null && !tipeBuku.isEmpty()) {
            return BukuFactory.buatBuku(tipeBuku, idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
        }
        
        // Default jika tipe tidak diset (membuat instance Buku secara umum)
        return new Buku(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
    }
}
