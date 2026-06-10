package com.perpustakaan.patterns.structural.decorator;

import com.perpustakaan.model.Buku;

public class BukuDecorator extends Buku {
    protected Buku buku;

    public BukuDecorator(Buku buku) {
        super(buku.getIdBuku(), buku.getJudul(), buku.getPenulis(), buku.getGenre(), buku.getDurasiHariPeminjaman(),
                buku.getHargaBeli());
        this.buku = buku;
    }

    @Override
    public int getIdBuku() {
        return buku.getIdBuku();
    }

    @Override
    public void setIdBuku(int idBuku) {
        this.buku.setIdBuku(idBuku);
        this.idBuku = idBuku;
    }

    @Override
    public String getJenisBuku() {
        // TODO Auto-generated method stub
        return buku.getJenisBuku();
    }

    @Override
    public double getBiayaDenda() {
        // TODO Auto-generated method stub
        return buku.getBiayaDenda();
    }
}
