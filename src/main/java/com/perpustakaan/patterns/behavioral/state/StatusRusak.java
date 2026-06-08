package com.perpustakaan.patterns.behavioral.state;

import com.perpustakaan.model.Buku;

public class StatusRusak implements StatusBuku{
    @Override
    public void pinjam(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " tidak bisa dipinjam karena dalam kondisi rusak");
    }

    @Override
    public void kembalikan(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " tidak dapat dikembalikan karena tidak dipinjam");
    }

    @Override
    public void tandaiKerusakan(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " sudah dalam kondisi rusak");
    }

    @Override
    public String getStatusBuku() {
        // TODO Auto-generated method stub
        return "Rusak";
    }
}
