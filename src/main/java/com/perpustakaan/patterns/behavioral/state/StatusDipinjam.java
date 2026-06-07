package com.perpustakaan.patterns.behavioral.state;

import com.*;
import com.perpustakaan.model.Buku;;

public class StatusDipinjam implements StatusBuku {
    @Override
    public void pinjam(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " tidak dapat dipinjam karena sedang dipinjam orang lain");
    }
    
    @Override
    public void kembalikan(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " berhasil dikembalikan");
        buku.setStatusBuku(new StatusTersedia());
    }
    
    @Override
    public void tandaiKerusakan(Buku buku) {
        // TODO Auto-generated method stub
        System.out.println("Buku " + buku.getJudul() + " menjadi rusak");
        buku.setStatusBuku(new StatusRusak());
    }
    
    @Override
    public String getStatusBuku() {
        // TODO Auto-generated method stub
        return "Dipinjam";
    }
}
