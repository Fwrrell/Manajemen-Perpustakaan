package com.perpustakaan.patterns.creational.builder;

import com.perpustakaan.model.BukuKoleksiSpesial;

public class BukuBuilder {
    public final int idBuku;
    public final String judul;
    public final String penulis;
    public final String genre;
    public final int batasHariPeminjaman;
    public final double hargaBeli;

    // param opsional
    public String isbn = "";
    public String tahunTerbit = "";
    public String penerbit = "";
    public String lokasiRak = "";
    public String edisi = "";

    public BukuBuilder(int idBuku, String judul, String penulis, String genre, int batasHariPeminjaman,
            double hargaBeli) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.batasHariPeminjaman = batasHariPeminjaman;
        this.hargaBeli = hargaBeli;
    }

    public BukuBuilder setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BukuBuilder setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
        return this;
    }

    public BukuBuilder setPenerbit(String penerbit) {
        this.penerbit = penerbit;
        return this;
    }

    public BukuBuilder setLokasiRak(String lokasiRak) {
        this.lokasiRak = lokasiRak;
        return this;
    }

    public BukuBuilder setEdisi(String edisi) {
        this.edisi = edisi;
        return this;
    }

    public BukuKoleksiSpesial build() {
        return new BukuKoleksiSpesial(this);
    }
}
