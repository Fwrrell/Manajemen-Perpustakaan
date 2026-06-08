package com.perpustakaan.model;

import java.util.List;

public class Member {
    private String nama;
    private String email;
    private String noTelpon;
    private String idPengguna;
    private boolean isBlocked;
    private List<Peminjaman> bukuDipinjam;
    private List<Peminjaman> riwayatPeminjaman;

    public Member(String nama, String email, String noTelpon, String idPengguna, boolean isBlocked, List<Peminjaman> bukuDipinjam, List<Peminjaman> riwayatPeminjamann) {
        this.nama = nama;
        this.email = email;
        this.noTelpon = noTelpon;
        this.idPengguna = idPengguna;
        this.isBlocked = isBlocked;
        this.bukuDipinjam = bukuDipinjam;
        this.riwayatPeminjaman = riwayatPeminjamann;
    }

    public String getIdPengguna() {
        return this.idPengguna;
    }

    public String getNama() {
        return this.nama;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNoTelpon() {
        return this.noTelpon;
    }
    
    public boolean isBlocked() {
        return this.isBlocked;
    }

    public int getJumlahBukuDipinjam() {
        return this.bukuDipinjam.size();
    }

    public List<Peminjaman> getRiwayatPeminjamann() {
        return riwayatPeminjaman;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelpon(String noTelpon) {
        this.noTelpon = noTelpon;
    }

    public void setBlocked(boolean blocked) {
        this.isBlocked = blocked;
    }

    public void tambahBukuDipinjam(Peminjaman buku) {
        bukuDipinjam.add(buku);
    }

    public void pengembalianBukuDipinjam(Peminjaman buku) {
        buku.ubahStatusPeminjaman();
        bukuDipinjam.remove(buku);
        riwayatPeminjaman.add(buku);
    }    
}
