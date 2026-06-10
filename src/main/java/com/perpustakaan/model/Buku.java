package com.perpustakaan.model;

import com.perpustakaan.patterns.behavioral.state.StatusBuku;
import com.perpustakaan.patterns.behavioral.state.StatusTersedia;

public abstract class Buku {
    protected int idBuku;
    protected String judul;
    protected String penulis;
    protected String genre;
    protected int batasHariPeminjaman;
    protected double hargaBeli;
    protected double hargaSewa;
    protected double hargaDenda;
    protected StatusBuku statusBuku;

    public Buku(int idBuku, String judul, String penulis, String genre, int batasHariPeminjaman, double hargaBeli) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.batasHariPeminjaman = batasHariPeminjaman;
        this.hargaBeli = hargaBeli;
        this.hargaSewa = (hargaBeli / 10);
        this.hargaDenda = (hargaBeli / 10);
        this.statusBuku = new StatusTersedia();
    }

    public int getIdBuku() {
        return this.idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
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

    public double getHargaBeli() {
        return this.hargaBeli;
    }

    public StatusBuku getStatusBuku() {
        return this.statusBuku;
    }

    public double getBiayaDenda() {
        return this.hargaDenda;
    }

    public double getHargaSewa() {
        return this.hargaSewa;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBatasHariPeminjaman(int batasHariPeminjaman) {
        this.batasHariPeminjaman = batasHariPeminjaman;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public void setStatusBuku(StatusBuku status) {
        this.statusBuku = status;
    }

    public void pinjam() {
        this.statusBuku.pinjam(this);
    }

    public void kembalikan() {
        this.statusBuku.kembalikan(this);
    }

    public void tandaiKerusakan() {
        this.statusBuku.tandaiKerusakan(this);
    }

    public abstract String getJenisBuku();

    public String getDetailInfo() {
        return "--------------------------------------------------\n" +
                "ID Buku       : " + idBuku + "\n" +
                "Judul         : " + judul + "\n" +
                "Penulis       : " + penulis + "\n" +
                "Genre         : " + genre + "\n" +
                "Jenis         : " + getJenisBuku() + "\n" +
                "Batas Pinjam  : " + batasHariPeminjaman + " hari\n" +
                "Harga Beli    : Rp" + hargaBeli + "\n" +
                "Denda/hari    : Rp" + getBiayaDenda() + "\n" +
                "Status        : " + statusBuku.getStatusBuku() + "\n" +
                "--------------------------------------------------";
    }

    @Override
    public String toString() {
        return "[" + idBuku + "] " + judul + " - " + penulis + " - Jenis Buku " + this.getJenisBuku()
                + " - Status Buku " + this.statusBuku.getStatusBuku();
    }
}
