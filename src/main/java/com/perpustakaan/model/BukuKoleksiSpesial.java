package com.perpustakaan.model;

import com.perpustakaan.patterns.creational.builder.BukuBuilder;

public class BukuKoleksiSpesial extends Buku {
    private String isbn;
    private String tahunTerbit;
    private String penerbit;
    private String lokasiRak;
    private String edisi;

    public BukuKoleksiSpesial(BukuBuilder builder) {
        super(builder.idBuku, builder.judul, builder.penulis, builder.genre, builder.batasHariPeminjaman,
                builder.hargaBeli);
        this.isbn = builder.isbn;
        this.tahunTerbit = builder.tahunTerbit;
        this.penerbit = builder.penerbit;
        this.lokasiRak = builder.lokasiRak;
        this.edisi = builder.edisi;
    }

    @Override
    public String getJenisBuku() {
        return "Koleksi Spesial";
    }

    public String getInfoSpesial() {
        return "ISBN:" + (isbn.isEmpty() ? "-" : isbn) +
                "|Thn:" + (tahunTerbit.isEmpty() ? "-" : tahunTerbit) +
                "|Pub:" + (penerbit.isEmpty() ? "-" : penerbit) +
                "|Rak:" + (lokasiRak.isEmpty() ? "-" : lokasiRak) +
                "|Edisi:" + (edisi.isEmpty() ? "-" : edisi);
    }

    @Override
    public String getDetailInfo() {
        String baseInfo = super.getDetailInfo();
        // Mencari baris terakhir (garis pemisah) untuk disisipkan informasi tambahan sebelum garis tersebut
        return baseInfo.replaceAll("--------------------------------------------------$",
                "ISBN          : " + (isbn.isEmpty() ? "Tidak Ada" : isbn) + "\n" +
                        "Tahun Terbit  : " + (tahunTerbit.isEmpty() ? "Tidak Ada" : tahunTerbit) + "\n" +
                        "Penerbit      : " + (penerbit.isEmpty() ? "Tidak Ada" : penerbit) + "\n" +
                        "Rak           : " + (lokasiRak.isEmpty() ? "Tidak Ada" : lokasiRak) + "\n" +
                        "Edisi         : " + (edisi.isEmpty() ? "Tidak Ada" : edisi) + "\n" +
                        "--------------------------------------------------");
    }
}