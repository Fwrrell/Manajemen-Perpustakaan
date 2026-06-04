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

    public Member(String nama, String email, String noTelpon, String idPengguna, boolean isBlocked,
            List<Peminjaman> bukuDipinjam, List<Peminjaman> riwayatPeminjamann) {
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

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public int getJumlahBukuDipinjam() {
        return this.bukuDipinjam.size();
    }

    public void tambahBukuDipinjam(Peminjaman x) {
        bukuDipinjam.add(x);
    }

    public List<Peminjaman> getRiwayPeminjamann() {
        return riwayatPeminjaman;
    }
}
