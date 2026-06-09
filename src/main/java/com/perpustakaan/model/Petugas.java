package com.perpustakaan.model;

public class Petugas {
    private String idPetugas;
    private String nama;
    private String email;

    public Petugas(String idPetugas, String nama, String email) {
        this.idPetugas = idPetugas;
        this.nama = nama;
        this.email = email;
    }

    public String getIdPetugas() {
        return this.idPetugas;
    }

    public String getNama() {
        return this.nama;
    }

    public String getEmail() {
        return this.email;
    }
}
