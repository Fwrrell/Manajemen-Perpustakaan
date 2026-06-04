package main.Model;

public class BukuPelajaran extends Buku{
    private String subjek;

    public BukuPelajaran(String idBuku, String judul, String penulis, String genre, int batasHariPeminjaman, double hargaBuku, String subjek){
        super(idBuku, judul, penulis, genre, batasHariPeminjaman, hargaBuku);
        this.subjek = subjek;
    }
}
