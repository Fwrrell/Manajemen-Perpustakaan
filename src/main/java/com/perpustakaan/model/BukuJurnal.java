package com.perpustakaan.model;

public class BukuJurnal extends Buku{
    private String idJurnal;

    public BukuJurnal(String judul, String idBuku, String penulis, String genre, int batasHariPeminjaman,Double hargaBeli, String idJurnal) {
        super(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
        this.idJurnal = idJurnal;
    }
    

}
