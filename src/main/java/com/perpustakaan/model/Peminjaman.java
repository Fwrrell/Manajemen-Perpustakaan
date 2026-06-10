package com.perpustakaan.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Peminjaman {
    private int idPeminjaman;
    private Member member;
    private Buku bukuDipinjam;
    private LocalDate tanggalPeminjaman;
    private LocalDate tanggalPengembalian;   
    private LocalDate tanggalAkhirPeminjaman;
    private double jumlahDibayar;
    private boolean statusPeminjaman;        

    public Peminjaman(int idPeminjaman, Member member, Buku bukuDipinjam, LocalDate tanggalPeminjaman) {
        this.idPeminjaman = idPeminjaman;
        this.member = member;
        this.bukuDipinjam = bukuDipinjam;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalAkhirPeminjaman = tanggalPeminjaman.plusDays(bukuDipinjam.getDurasiHariPeminjaman());
        this.tanggalPengembalian = null;
        this.jumlahDibayar = 0;
        this.statusPeminjaman = true;
    }

    public void pengembalian(LocalDate tanggal) {
        this.tanggalPengembalian = tanggal;
        this.statusPeminjaman = false;
    }

    public boolean melebihiBatasWaktu() {
        return tanggalPengembalian.isAfter(tanggalAkhirPeminjaman);
    }

    public double hitungDendaKeterlambatan() {
        if (!melebihiBatasWaktu()) {
            return 0;
        }
        long jumlahHariTelat = ChronoUnit.DAYS.between(tanggalAkhirPeminjaman, tanggalPengembalian);
        return bukuDipinjam.getBiayaDenda() * jumlahHariTelat;
    }

    public double hitungTotalBiayaSewa() {
        return bukuDipinjam.getHargaSewa() + hitungDendaKeterlambatan();
    }

    public void bayar(double jumlahDibayar) {
        this.jumlahDibayar += jumlahDibayar;
    }

    public boolean isLunas() {
        return jumlahDibayar >= hitungTotalBiayaSewa();
    }

    public void ubahStatusPeminjaman() {
        this.statusPeminjaman = false;
    }

    public int getIdPeminjaman() {
        return this.idPeminjaman;
    }

    public Member getMember() {
        return this.member;
    }

    public Buku getBukuDipinjam() {
        return this.bukuDipinjam;
    }

    public LocalDate getTanggalPeminjaman() {
        return this.tanggalPeminjaman;
    }

    public LocalDate getTanggalPengembalian() {
        return this.tanggalPengembalian;
    }

    public LocalDate getTanggalAkhirPeminjaman() {
        return this.tanggalAkhirPeminjaman;
    }

    public double getJumlahDibayar() {
        return this.jumlahDibayar;
    }

    public boolean getStatusPeminjaman() {
        return this.statusPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public void detailPeminjaman() {
        System.out.println("==================================================");
        System.out.println("ID Peminjaman      : " + idPeminjaman);
        System.out.println("Peminjam           : " + member.getNama());
        System.out.println("Buku               : " + bukuDipinjam.getJudul());
        System.out.println("Tanggal Pinjam     : " + tanggalPeminjaman);
        System.out.println("Jatuh Tempo        : " + tanggalAkhirPeminjaman);
        System.out.println("Tanggal Kembali    : " + (tanggalPengembalian != null ? tanggalPengembalian : "-"));
        System.out.println("Status             : " + (statusPeminjaman ? "Masih Dipinjam" : "Selesai"));
        if (tanggalPengembalian != null) {
            System.out.println("Denda              : Rp" + hitungDendaKeterlambatan());
            System.out.println("Total Bayar        : Rp" + hitungTotalBiayaSewa());
        }
        System.out.println("==================================================");
    }

    @Override
    public String toString() {
        return "[" + idPeminjaman + "] " + bukuDipinjam.getJudul()
                + " oleh " + member.getNama()
                + " (" + (statusPeminjaman ? "Dipinjam" : "Buku Sudah Dikembalikan") + ")";
    }
}
