package com.perpustakaan.model;

import java.time.LocalDate;

public class Peminjaman {
    private String idPeminjaman;
    private Member pengguna;
    private Buku bukuDipinjam;
    private String tanggalPeminjaman;
    private String tanggalPengembalian;
    private String tanggalAkhirPeminjaman;
    private double biayaDenda;
    private double jumlahDibayar;
    private boolean statusPeminjaman;

    public Peminjaman(String idPeminjaman, Member pengguna, Buku bukuDipinjam, String tanggalPeminjaman, String tanggalAkhirPeminjaman) {
        this.idPeminjaman = idPeminjaman;
        this.pengguna = pengguna;
        this.bukuDipinjam = bukuDipinjam;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalAkhirPeminjaman = tanggalAkhirPeminjaman;
        this.tanggalPengembalian = null;
        this.biayaDenda = 0;
        this.jumlahDibayar = 0;
        this.statusPeminjaman = true;
    }

    // public double HitungDendaKeterlambatan(String tanggalPengembalian) {
    //     this.tanggalPengembalian = tanggalPengembalian;
    // }

    public void ubahStatusPeminjaman() {
        statusPeminjaman = false;
    }

    public boolean getStatusPeminjaman() {
        return this.statusPeminjaman;
    }

    // public boolean melebihiBatasWaktu() {

    // }

}
