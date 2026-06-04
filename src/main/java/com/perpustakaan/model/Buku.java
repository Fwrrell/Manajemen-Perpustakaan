package com.perpustakaan.model;

public class Buku {
    protected String idBuku;
    protected String judul;
    protected String penulis;
    protected String genre;
    protected int batasHariPeminjaman;
    protected double hargaBeli;
    //protected StatusBuku statusBuku;

    public Buku(String idBuku, String judul, String penulis, String genre, int batasHariPeminjaman, double hargaBeli) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.batasHariPeminjaman = batasHariPeminjaman;
        this.hargaBeli = hargaBeli;
    }

    public String getIdBuku() {
        return this.idBuku;
    }

    public String getJudul() {
        return this.judul;
    }

    public String getPenulis() {
        return this.penulis;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getDurasiHariPeminjaman() {
        return this.batasHariPeminjaman;
    }

    // public StatusBuku getStatusBuku() {

    // }

    public double getBiayaDenda() {
        return (this.hargaBeli / 10);
    }
}
