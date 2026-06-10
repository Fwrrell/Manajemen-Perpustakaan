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
    private double dendaKerusakan;

    public Peminjaman(int idPeminjaman, Member member, Buku bukuDipinjam, LocalDate tanggalPeminjaman) {
        this.idPeminjaman = idPeminjaman;
        this.member = member;
        this.bukuDipinjam = bukuDipinjam;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalAkhirPeminjaman = tanggalPeminjaman.plusDays(bukuDipinjam.getDurasiHariPeminjaman());
        this.tanggalPengembalian = null;
        this.jumlahDibayar = 0;
        this.statusPeminjaman = true;
        this.dendaKerusakan = 0;
    }

    public void setPengembalian(LocalDate tanggal) {
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
        return bukuDipinjam.getHargaSewa() + hitungDendaKeterlambatan() + this.dendaKerusakan;
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

    public double getDendaKerusakan() {
        return this.dendaKerusakan;
    }

    public boolean getStatusPeminjaman() {
        return this.statusPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public String detailPeminjaman() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("ID Peminjaman      : ").append(idPeminjaman).append("\n");
        sb.append("Peminjam           : ").append(member.getNama()).append("\n");
        sb.append("Buku               : ").append(bukuDipinjam.getJudul()).append("\n");
        sb.append("Tanggal Pinjam     : ").append(tanggalPeminjaman).append("\n");
        sb.append("Jatuh Tempo        : ").append(tanggalAkhirPeminjaman).append("\n");
        sb.append("Tanggal Kembali    : ").append(tanggalPengembalian != null ? tanggalPengembalian : "-").append("\n");
        sb.append("Status             : ").append(statusPeminjaman ? "Masih Dipinjam" : "Selesai");
        if (tanggalPengembalian == null && jumlahDibayar > 0) {
            sb.append("Uang Deposit   : Rp").append(jumlahDibayar).append("\n");
        }
        if (tanggalPengembalian != null) {
            sb.append("Denda          : Rp").append(hitungDendaKeterlambatan()).append("\n");
            sb.append("Total Bayar    : Rp").append(hitungTotalBiayaSewa()).append("\n");
        }
        sb.append("==================================================");
        return sb.toString();
    }

    public void rincianBiaya() {
        System.out.println("==================================================");
        System.out.println("Biaya Sewa       : Rp" + bukuDipinjam.getHargaSewa());
        System.out.println("Denda Telat      : Rp" + hitungDendaKeterlambatan());
        System.out.println("Biaya Kerusakan  : Rp" + getDendaKerusakan());
        System.out.println("TOTAL BAYAR    : Rp" + hitungTotalBiayaSewa());
        System.out.println("==================================================");
    }

    @Override
    public String toString() {
        return "[" + idPeminjaman + "] " + bukuDipinjam.getJudul()
                + " oleh " + member.getNama()
                + " (" + (statusPeminjaman ? "Dipinjam" : "Buku Sudah Dikembalikan") + ")";
    }
}
