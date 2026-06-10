package com.perpustakaan.model;

public class BukuPelajaran extends Buku {
    private String subjek;

    public BukuPelajaran(int idBuku, String judul, String penulis, String genre, int batasHariPeminjaman,
            double hargaBeli, String subjek) {
        super(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBeli);
        this.subjek = subjek;
    }

    public String getSubjek() {
        return this.subjek;
    }

    public void setSubjek(String subjek) {
        this.subjek = subjek;
    }

    @Override
    public String getJenisBuku() {
        return "Pelajaran";
    }

    @Override
    public String getDetailInfo() {
        return super.getDetailInfo().replaceAll("--------------------------------------------------$",
                "Subjek        : " + subjek + "\n--------------------------------------------------");
    }
}
