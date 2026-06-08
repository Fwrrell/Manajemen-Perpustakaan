package com.perpustakaan.model;

public class BukuJurnal extends Buku {
    private String idJurnal;

    public BukuJurnal(String idBuku, String judul, String penulis, String genre, int batasHariPeminjaman,
            double hargaBeli, String idJurnal) {
        super(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
        this.idJurnal = idJurnal;
    }

    public String getIdJurnal() {
        return this.idJurnal;
    }

    public void setIdJurnal(String idJurnal) {
        this.idJurnal = idJurnal;
    }

    @Override
    public String getJenisBuku() {
        return "Jurnal";
    }

    @Override
    public String getDetailInfo() {
        return super.getDetailInfo().replace("--------------------------------------------------$",
                "ID Jurnal     : " + idJurnal + "\n--------------------------------------------------");
    }
}
